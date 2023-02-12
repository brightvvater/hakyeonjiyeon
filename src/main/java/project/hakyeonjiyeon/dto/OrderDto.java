package project.hakyeonjiyeon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.hakyeonjiyeon.domain.Lesson;
import project.hakyeonjiyeon.domain.Member;
import project.hakyeonjiyeon.domain.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {


    private LocalDateTime orderDate;



}
