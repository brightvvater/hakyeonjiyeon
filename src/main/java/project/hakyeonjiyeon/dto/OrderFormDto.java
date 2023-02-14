package project.hakyeonjiyeon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.hakyeonjiyeon.domain.PayMethod;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderFormDto {

    private Long memberId;

    private String name;

    private String phoneNumber;

    private Long lessonId;

    private int price;

    private String title;

    private PayMethod payMethod;

}
