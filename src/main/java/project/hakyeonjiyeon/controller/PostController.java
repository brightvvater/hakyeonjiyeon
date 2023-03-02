package project.hakyeonjiyeon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.hakyeonjiyeon.dto.BoardFormDto;
import project.hakyeonjiyeon.dto.PostCreateDto;
import project.hakyeonjiyeon.dto.PostFormDto;
import project.hakyeonjiyeon.security.CustomUser;
import project.hakyeonjiyeon.security.SessionUser;
import project.hakyeonjiyeon.repository.MemberRepository;
import project.hakyeonjiyeon.service.BoardService;
import project.hakyeonjiyeon.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("post")
@Slf4j
public class PostController {

    private final BoardService boardService;

    private final PostService postService;

    private final MemberRepository memberRepository;

    private final HttpSession httpSession;

    /*
     * 메인페이지
     */
    @GetMapping("/main")
    public String boardMain(Model model) {

        //게시판 목록??
        List<BoardFormDto> boardList = boardService.getBoardList();
        model.addAttribute("boardList", boardList);
        for (BoardFormDto boardFormDto : boardList) {
            log.info("board={}", boardFormDto.getName());
        }

        //게시물 목록
        List<PostFormDto> postList = postService.getListWithMember();
        model.addAttribute("postList", postList);

        return "board/main";
    }

    /*
     * 게시물 등록폼
     */
    @GetMapping("/addPost")
    public String addPostForm(Model model) {
        PostCreateDto postCreateDto = new PostCreateDto();

        List<BoardFormDto> boardList = boardService.getBoardList();
        postCreateDto.setBoardList(boardList);

        model.addAttribute("postCreateDto", postCreateDto);
        return "board/addPostForm";
    }

    /*
     * 게시글 등록
     */
    @PostMapping("/addPost")
    public String addPost(@Valid @ModelAttribute PostCreateDto postCreateDto, BindingResult bindingResult, Authentication authentication) {
        //validation!!
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);

            //게시판 조회
            List<BoardFormDto> boardList = boardService.getBoardList();
            postCreateDto.setBoardList(boardList);

            return "board/addPostForm";
        }

        //로그인멤버
        SessionUser member = (SessionUser) httpSession.getAttribute("member");
        Long memberId = 0L;
        if (member != null) {
            memberId = memberRepository.findByEmail(member.getEmail()).get().getId();
        }else if (authentication != null) {
            CustomUser userDetails = (CustomUser) authentication.getPrincipal();
            //log.info("userDetails={}", userDetails.getAuthId());
            //log.info("id={}", memberRepository.findIdByUserName(userDetails.getUsername()));
            memberId = memberRepository.findByAuthId(userDetails.getAuthId()).get().getId();
        }


        Long postId = postService.createPost(memberId, postCreateDto);
        return "redirect:/post/main";
    }

    /*
     * 게시물 상세 폼
     */
    @GetMapping("/detail/{postId}")
    public String postDetail(@PathVariable("postId") Long postId, Model model) {
        PostFormDto post = postService.getPostWithMember(postId);
        model.addAttribute("post", post);

        return "board/detail";
    }
}
