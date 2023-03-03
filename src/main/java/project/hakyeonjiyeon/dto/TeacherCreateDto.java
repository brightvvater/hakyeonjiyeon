package project.hakyeonjiyeon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import project.hakyeonjiyeon.domain.Teacher;
import project.hakyeonjiyeon.domain.TeacherFile;

import javax.persistence.Lob;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherCreateDto {

    private Long teacherId;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    @Pattern(regexp = "\\d{11}", message = "형식에 맞게 입력해주세요.")
    private String phoneNumber;

    @NotBlank(message = "강사 소개를 입력해주세요.")
    @Lob
    private String introduction;


    private List<MultipartFile> teacherFiles;

    private List<String> teacherFileList;


}
