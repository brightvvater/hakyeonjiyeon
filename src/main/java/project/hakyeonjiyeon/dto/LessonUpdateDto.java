package project.hakyeonjiyeon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonUpdateDto {

    private String title;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int price;

    @Lob
    private String content;

}
