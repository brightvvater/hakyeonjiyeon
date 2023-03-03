package project.hakyeonjiyeon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateDto {


    private String name;

    private String authId;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickName;

    @NotBlank(message = "휴대혼번호를 입력해주세요.")
    @Pattern(regexp = "\\d{11}", message = "형식에 맞게 입력해주세요.")
    private String phoneNumber;


    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

    @Email(message = "형식에 맞게 입력해주세요.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    //@Range(min = 8, max = 16)
    private String password;

}
