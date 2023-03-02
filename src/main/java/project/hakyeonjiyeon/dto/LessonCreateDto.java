package project.hakyeonjiyeon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import project.hakyeonjiyeon.domain.Category;
import project.hakyeonjiyeon.domain.Teacher;

import javax.persistence.Lob;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonCreateDto {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @NotNull(message = "시작일을 입력해주세요.")
    private LocalDateTime startDate;


    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @NotNull(message = "종료일을 입력해주세요.")
    private LocalDateTime endDate;

    @NotNull(message = "가격을 입력해주세요.")
    @Min(value = 10000, message = "최소 10000원 이상 입력해주세요.")
    private int price;

    @Lob
    private String content;

    private List<TeacherFormDto> teacherList;

    private List<CategoryFormDto> categoryList;

    @NotNull(message = "카테고리를 선택해주세요.")
    private Long categoryId;

    @NotNull(message = "강사를 선택해주세요.")
    private Long teacherId;

    @NotNull(message = "이미지를 최소 1개 이상 등록해주세요.")
    private List<MultipartFile> lessonFiles;
}
