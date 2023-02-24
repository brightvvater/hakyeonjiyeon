package project.hakyeonjiyeon.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.hakyeonjiyeon.domain.Board;
import project.hakyeonjiyeon.domain.Grade;
import project.hakyeonjiyeon.domain.Member;
import project.hakyeonjiyeon.domain.Post;
import project.hakyeonjiyeon.dto.MemberCreateDto;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        MemberCreateDto memberCreateDto = new MemberCreateDto("이름","아이디1","닉네임","010-9256-5814","주소","123","gildong@naver.com");
        Member member = Member.createMember(memberCreateDto, passwordEncoder);

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