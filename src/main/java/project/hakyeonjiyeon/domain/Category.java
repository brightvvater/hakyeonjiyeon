package project.hakyeonjiyeon.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.hakyeonjiyeon.dto.CategoryCreateDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    private void setId(Long id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

   @OneToMany(mappedBy = "category")
   private List<Lesson> lessons = new ArrayList<>();

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
        lesson.setCategory(this);
    }


    @Builder
    public Category(String name) {
        this.name = name;
    }

    public void update(CategoryCreateDto categoryCreateDto) {
        this.setName(categoryCreateDto.getName());
    }
}
