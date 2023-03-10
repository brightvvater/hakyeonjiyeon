package project.hakyeonjiyeon.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LessonFile {

    @Id
    @GeneratedValue
    @Column(name = "lesson_file_id")
    private Long id;


    @Enumerated(EnumType.STRING)
    private MainStatus mainStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    private String uploadFileName;

    private String storeFileName;

    @Builder
    public LessonFile(MainStatus mainStatus, String uploadFileName, String storeFileName) {
        this.mainStatus = mainStatus;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }



    private void setMainStatus(MainStatus mainStatus) {
        this.mainStatus = mainStatus;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    private void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    private void setStoreFileName(String storeFileName) {
        this.storeFileName = storeFileName;
    }
}
