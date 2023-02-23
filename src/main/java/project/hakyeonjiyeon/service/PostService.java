package project.hakyeonjiyeon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hakyeonjiyeon.domain.Board;
import project.hakyeonjiyeon.domain.Member;
import project.hakyeonjiyeon.domain.Post;
import project.hakyeonjiyeon.dto.PostCreateDto;
import project.hakyeonjiyeon.repository.BoardRepository;
import project.hakyeonjiyeon.repository.MemberRepository;
import project.hakyeonjiyeon.repository.PostRepository;

import java.time.LocalDateTime;

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
}
