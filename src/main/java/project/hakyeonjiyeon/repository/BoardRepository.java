package project.hakyeonjiyeon.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.hakyeonjiyeon.domain.Board;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BoardRepository {

    private final EntityManager em;

    public Long save(Board board) {
        em.persist(board);
        return board.getId();
    }

    public Board findById(Long boardId) {
        Board board = em.find(Board.class, boardId);
        return board;
    }

    public List<Board> findAll() {
        return em.createQuery(
                "select b from Board b"
        ).getResultList();
    }

    public void delete(Board board) {
        em.remove(board);
    }



}
