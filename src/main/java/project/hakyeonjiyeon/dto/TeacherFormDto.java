package project.hakyeonjiyeon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherFormDto {

    private Long teacherId;

    private String name;

    @Lob
    private String introduction;

}
