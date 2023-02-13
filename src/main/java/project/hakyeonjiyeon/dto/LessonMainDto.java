package project.hakyeonjiyeon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonMainDto {


    private Long lessonId;
    private String categoryName;

    private String title;

    private String teacherName;

    private String imageName;




}
