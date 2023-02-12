package project.hakyeonjiyeon.domain;

import lombok.AccessLevel;
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
}
