package project.hakyeonjiyeon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.hakyeonjiyeon.domain.Board;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDto {

    @NotNull(message = "게시판 선택은 필수입니다.")
    private Long boardId;

    @NotBlank(message = "제목을 입력해 주세요.")
    private String title;

    @Lob
    private String content;

    private List<BoardFormDto> boardList;

}
