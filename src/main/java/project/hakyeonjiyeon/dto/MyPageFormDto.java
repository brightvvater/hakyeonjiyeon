package project.hakyeonjiyeon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageFormDto {

    private Long memberId;

    private String memberName;

    private String title;

    private int price;

    private String teacherName;


}
