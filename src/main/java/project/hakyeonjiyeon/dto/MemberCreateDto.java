package project.hakyeonjiyeon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateDto {

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "아이디는 필수입니다.")
    private String authId;

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickName;

    private String phoneNumber;

    private String address;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @Email(message = "형식에 맞추어 입력해 주세요.")
    private String email;

    private String role;
}
