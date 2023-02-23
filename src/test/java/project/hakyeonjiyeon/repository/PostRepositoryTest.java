package project.hakyeonjiyeon.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.hakyeonjiyeon.domain.Board;
import project.hakyeonjiyeon.domain.Grade;
import project.hakyeonjiyeon.domain.Member;
import project.hakyeonjiyeon.domain.Post;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class PostRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("게시물 생성 테스트")
    void createPost() {
        //given
        Board board = createBoard();
        Member member = createMember();

        //when
        Post post = Post.builder()
                .title("게시물1")
                .board(board)
                .member(member)
                .content("게시물 내용1")
                .postDate(LocalDateTime.now())
                .build();

        Long savedId = postRepository.save(post);

        em.flush();
        em.clear();

        //then
        Assertions.assertThat(postRepository.findById(savedId).getContent()).isEqualTo(post.getContent());
        //Assertions.assertThat(postRepository.findById(savedId).getMember()).isEqualTo(member);???
    }

    private Member createMember() {
        Member member = Member.builder()
                .name("회원1")
                .nickName("닉네임")
                .address("주소1")
                .phoneNumber("010-1111-1111")
                .grade(Grade.MEMBER)
                .password("123")
                .build();

        memberRepository.save(member);
        return member;
    }

    private Board createBoard() {
        Board board = Board.builder()
                .name("게시판1")
                .build();

        boardRepository.save(board);
        return board;
    }
}