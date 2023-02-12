package project.hakyeonjiyeon.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.hakyeonjiyeon.dto.LessonUpdateDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lesson {

    @Id
    @GeneratedValue
    @Column(name = "lesson_id")
    private Long id;

    private String title;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int price;

    @Lob
    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy ="lesson" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LessonFile> lessonFiles = new ArrayList<>();//?? 양방향 매핑할 건지??


    @Builder
    public Lesson(String title, LocalDateTime startDate, LocalDateTime endDate, int price, String content, Teacher teacher, Category category) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.content = content;
        this.teacher = teacher;
        this.category = category;
        /*for (LessonFile lessonFile : lessonFiles) {
            this.lessonFiles.add(lessonFile);
        }*/
    }

    private void setId(Long id) {
        this.id = id;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    private void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    private void setPrice(int price) {
        this.price = price;
    }

    private void setContent(String content) {
        this.content = content;
    }

    private void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    private void setCategory(Category category) {
        this.category = category;
    }

    private void setLessonFiles(List<LessonFile> lessonFiles) {
        this.lessonFiles = lessonFiles;
    }

    public Long updateLesson(LessonUpdateDto lessonUpdateDto) {
        this.setTitle(lessonUpdateDto.getTitle());
        this.setStartDate(lessonUpdateDto.getStartDate());
        this.setEndDate(lessonUpdateDto.getEndDate());
        this.setContent(lessonUpdateDto.getContent());
        this.setPrice(lessonUpdateDto.getPrice());
        return this.getId();
    }



}
