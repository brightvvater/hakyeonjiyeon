package project.hakyeonjiyeon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.hakyeonjiyeon.domain.PayMethod;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDto {


    private Long memberId;

    private Long lessonId;
    private LocalDateTime orderDate;

    @NotBlank(message = "결제 방식을 선택해 주세요.")
    private PayMethod payMethod;



}
