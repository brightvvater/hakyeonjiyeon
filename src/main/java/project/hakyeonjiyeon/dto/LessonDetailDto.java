package project.hakyeonjiyeon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonDetailDto {

    private Long lessonId;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private int price;

    @Lob
    private String content;

    private String categoryName;

    private String teacherName;

    private String introduction;

    private List<String> teacherImages;

    private List<String> lessonImages;



}
