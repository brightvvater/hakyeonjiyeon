package project.hakyeonjiyeon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonCreateDto {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "시작일을 입력해주세요.")
    private LocalDateTime startDate;

    @NotBlank(message = "종료일을 입력해주세요.")
    private LocalDateTime endDate;

    @NotBlank(message = "가격을 입력해주세요.")
    private int price;

    @Lob
    private String content;
}
