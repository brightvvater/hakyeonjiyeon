package project.hakyeonjiyeon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import project.hakyeonjiyeon.domain.Category;
import project.hakyeonjiyeon.domain.Teacher;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonCreateDto {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;


    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    @NotBlank(message = "가격을 입력해주세요.")
    private int price;

    @Lob
    private String content;

    private List<TeacherFormDto> teacherList;

    private List<CategoryFormDto> categoryList;

    private Long categoryId;

    private Long teacherId;
}
