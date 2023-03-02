package project.hakyeonjiyeon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateDto {

    private Long categoryId;
    @NotBlank(message = "카테고리 제목은 필수입니다.")
    private String name;

}
