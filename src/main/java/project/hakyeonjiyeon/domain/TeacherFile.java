package project.hakyeonjiyeon.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherFile {

    @Id
    @GeneratedValue
    @Column(name = "teacher_file_id")
    private Long id;


    @Enumerated(EnumType.STRING)
    private MainStatus mainStatus;

    private String uploadFileName;

    private String storeFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Builder
    public TeacherFile(MainStatus mainStatus, String uploadFileName, String storeFileName) {
        this.mainStatus = mainStatus;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }



    private void setId(Long id) {
        this.id = id;
    }

    private void setMainStatus(MainStatus mainStatus) {
        this.mainStatus = mainStatus;
    }

    private void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    private void setStoreFileName(String storeFileName) {
        this.storeFileName = storeFileName;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
