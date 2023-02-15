package project.hakyeonjiyeon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonMainDto {


    private Long lessonId;
    private String categoryName;

    private String title;

    private String teacherName;

    private String imageName;

    private List<CategoryFormDto> categoryList;




}
