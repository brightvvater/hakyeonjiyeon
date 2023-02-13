package project.hakyeonjiyeon.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.hakyeonjiyeon.domain.Category;
import project.hakyeonjiyeon.domain.Lesson;
import project.hakyeonjiyeon.domain.Teacher;
import project.hakyeonjiyeon.dto.LessonMainDto;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class LessonRepositoryTest {
    
    @Autowired
    private LessonRepository lessonRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("레슨 및 강사 카테고리 조인테스트 ")
    void findLessonWithTeacherAndCategory() {
        //given
        //강사 생성
        Long teacherId = createTeacher();
        //카테고리 생성
        Long categoryId = createCategory();
        //레슨 생성
        String title ="레슨제목";
        Lesson lesson = Lesson.builder()
                .price(10000)
                .title(title)
                .content("레슨 내용")
                .category(categoryRepository.findById(categoryId))
                .teacher(teacherRepository.findById(teacherId))
                .build();

        lessonRepository.save(lesson);

        //when
        //레슨 조회
        List<Lesson> lessonList = lessonRepository.findLessonWithTeacherAndCategory();

        //then
        for (Lesson lesson1 : lessonList) {
            System.out.println("lesson1.getCategory().getName() = " + lesson1.getCategory().getName());
            System.out.println("lesson1.getTeacher().getName() = " + lesson1.getTeacher().getName());
        }



    }

    @Test
    @DisplayName("레슨 상세조회 테스트")
    public void findLessonById() {
        //given
        //강사 생성
        Long teacherId = createTeacher();
        //카테고리 생성
        Long categoryId = createCategory();
        //레슨 생성
        String title ="레슨제목";
        Lesson lesson = Lesson.builder()
                .price(10000)
                .title(title)
                .content("레슨 내용")
                .category(categoryRepository.findById(categoryId))
                .teacher(teacherRepository.findById(teacherId))
                .build();

        lessonRepository.save(lesson);
        em.flush();
        em.clear();
        //when
        Lesson lessonDetail = lessonRepository.findLessonDetail(lesson.getId());
        //then
        assertThat(lessonDetail.getTitle()).isEqualTo(title);
        assertThat(lessonDetail.getTeacher().getId()).isEqualTo(teacherId);
    }
    

    private Long createCategory() {
        Category category = Category.builder()
                .name("category1")
                .build();
        categoryRepository.create(category);
        return category.getId();
    }

    private Long createTeacher() {
        Teacher teacher = Teacher.builder()
                .name("강사1")
                .phoneNumber("010-111-1111")
                .introduction("강사 소개1")
                .build();
        
        teacherRepository.save(teacher);
        return teacher.getId();
    }

    
}