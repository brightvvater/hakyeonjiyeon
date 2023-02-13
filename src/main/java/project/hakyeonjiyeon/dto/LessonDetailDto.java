package project.hakyeonjiyeon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonDetailDto {

    private Long lessonId;

    private String title;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int price;

    @Lob
    private String content;

    private String categoryName;

    private String teacherName;

    private String introduction;


}
