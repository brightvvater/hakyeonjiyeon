package project.hakyeonjiyeon.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.hakyeonjiyeon.dto.TeacherCreateDto;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

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

    @Lob
    private String introduction;

    @OneToMany(mappedBy ="teacher" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeacherFile> teacherFiles = new ArrayList<>();

    @OneToMany(mappedBy = "teacher")
    private List<Lesson> lessons = new ArrayList<>();



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

    //==연관관계 편의 메서드==//
    public void addTeacherFile(TeacherFile teacherFile) {
        teacherFiles.add(teacherFile);
        teacherFile.setTeacher(this);

    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
        lesson.setTeacher(this);
    }


    @Builder
    public Teacher(String name, String phoneNumber, String introduction, List<TeacherFile> teacherFiles) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;
        for (TeacherFile teacherFile : teacherFiles) {
            addTeacherFile(teacherFile);
        }
    }



    public Long updateTeacher(TeacherCreateDto teacherCreateDto) {
        this.setName(teacherCreateDto.getName());
        this.setPhoneNumber(teacherCreateDto.getPhoneNumber());
        this.setIntroduction(teacherCreateDto.getIntroduction());
        return  this.getId();
    }
}
