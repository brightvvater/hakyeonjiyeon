package project.hakyeonjiyeon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hakyeonjiyeon.domain.Board;
import project.hakyeonjiyeon.domain.Category;
import project.hakyeonjiyeon.dto.BoardCreateDto;
import project.hakyeonjiyeon.dto.BoardFormDto;
import project.hakyeonjiyeon.repository.BoardRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    //게시판 등록
    @Transactional
    public Long createBoard(BoardCreateDto boardCreateDto) {
        Board board = Board.builder()
                .name(boardCreateDto.getName())
                .build();

        Long boardId = boardRepository.save(board);
        return boardId;

    }

    //게시판 전체 조회
    public List<BoardFormDto> getBoardList() {
        List<Board> boardList = boardRepository.findAll();

        List<BoardFormDto> list = new ArrayList<>();
        for (Board board : boardList) {
            BoardFormDto boardFormDto = new BoardFormDto();
            boardFormDto.setBoardId(board.getId());
            boardFormDto.setName(board.getName());

            list.add(boardFormDto);
        }
        return list;
    }

    //게시판 삭제
    @Transactional
    public void remove(Long boardId) throws SQLException {

        Board board = boardRepository.findById(boardId);
        /*for (Category category : categories) {
            log.info("category={}", category.getLessons());
        }*/
        if(!board.getPosts().isEmpty()){
            throw new SQLException();
        }
        boardRepository.delete(board);
    }
}
