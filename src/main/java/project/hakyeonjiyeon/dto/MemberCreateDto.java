package project.hakyeonjiyeon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

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

    @NotBlank(message = "휴대혼번호를 입력해주세요.")
    @Pattern(regexp = "\\d{11}", message = "형식에 맞게 입력해주세요.")
    private String phoneNumber;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

    @NotBlank(message = "비밀번호는 필수입니다.")
    //@Range(min = 8, max = 16)
    private String password;


    @Email(message = "형식에 맞추어 입력해 주세요.")
    private String email;

}
