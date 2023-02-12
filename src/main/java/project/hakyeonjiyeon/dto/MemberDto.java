package project.hakyeonjiyeon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    private String nickName;

    private String phoneNumber;

    private String address;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
