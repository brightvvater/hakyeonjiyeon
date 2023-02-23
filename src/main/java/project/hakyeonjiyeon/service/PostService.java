package project.hakyeonjiyeon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hakyeonjiyeon.domain.Board;
import project.hakyeonjiyeon.domain.Member;
import project.hakyeonjiyeon.domain.Post;
import project.hakyeonjiyeon.dto.PostCreateDto;
import project.hakyeonjiyeon.dto.PostFormDto;
import project.hakyeonjiyeon.repository.BoardRepository;
import project.hakyeonjiyeon.repository.MemberRepository;
import project.hakyeonjiyeon.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final MemberRepository memberRepository;

    private final BoardRepository boardRepository;

    private final PostRepository postRepository;

    //게시물 등록
    @Transactional
    public Long createPost(Long memberId, PostCreateDto postCreateDto) {
        //회원 조회
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        //게시판 조회
        Board board = boardRepository.findById(postCreateDto.getBoardId());

        //게시물 등록
        Post post = Post.builder()
                .title(postCreateDto.getTitle())
                .content(postCreateDto.getContent())
                .board(board)
                .member(member)
                .postDate(LocalDateTime.now())
                .build();

        Long postId = postRepository.save(post);
        return postId;
    }

    //게시물 전체 조회
    public List<PostFormDto> getListWithMember() {
        List<Post> posts = postRepository.findAll();

        List<PostFormDto> list = new ArrayList<>();
        for (Post post : posts) {
            PostFormDto postFormDto = new PostFormDto();
            postFormDto.setTitle(post.getTitle());
            postFormDto.setContent(post.getContent());
            postFormDto.setPostDate(post.getPostDate());
            postFormDto.setPostId(post.getId());
            postFormDto.setNickName(post.getMember().getNickName());
            postFormDto.setBoardName(post.getBoard().getName());

            list.add(postFormDto);
        }

        return list;
    }

    //특정 게시물 조회
    public PostFormDto getPostWithMember(Long postId) {

        Post post = postRepository.findByIdWithMember(postId);
        PostFormDto postFormDto = new PostFormDto();
        postFormDto.setTitle(post.getTitle());
        postFormDto.setContent(post.getContent());
        postFormDto.setPostDate(post.getPostDate());
        postFormDto.setPostId(post.getId());
        postFormDto.setNickName(post.getMember().getNickName());
        postFormDto.setBoardName(post.getBoard().getName());

        return postFormDto;
    }

}
