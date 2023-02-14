package project.hakyeonjiyeon.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.hakyeonjiyeon.dto.TeacherCreateDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Teacher {

    @Id
    @GeneratedValue
    @Column(name = "teacher_id")
    private Long id;

    private String name;

    private String phoneNumber;

    private String introduction;

    private void setId(Long id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Builder
    public Teacher(String name, String phoneNumber, String introduction) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;

    }


    public Long updateTeacher(TeacherCreateDto teacherCreateDto) {
        this.setName(teacherCreateDto.getName());
        this.setPhoneNumber(teacherCreateDto.getPhoneNumber());
        this.setIntroduction(teacherCreateDto.getIntroduction());
        return  this.getId();
    }
}
