package project.hakyeonjiyeon.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.hakyeonjiyeon.domain.Board;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("게시판 생성 테스트")
    void createBoard() {
        //given
        Board board = Board.builder()
                .name("게시판1")
                .build();


        //when
        Long savedId = boardRepository.save(board);

        em.flush();
        em.clear();

        //then
        Assertions.assertThat(boardRepository.findById(savedId).getName()).isEqualTo(board.getName());
    }
}
