package project.hakyeonjiyeon.service;

import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.hakyeonjiyeon.domain.Category;
import project.hakyeonjiyeon.domain.Lesson;
import project.hakyeonjiyeon.domain.Teacher;
import project.hakyeonjiyeon.dto.LessonDto;
import project.hakyeonjiyeon.dto.LessonUpdateDto;
import project.hakyeonjiyeon.repository.CategoryRepository;
import project.hakyeonjiyeon.repository.LessonRepository;
import project.hakyeonjiyeon.repository.TeacherRepository;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class LessonServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TeacherRepository teacherRepository;



    @Test
    @DisplayName("레슨등록 테스트")
    public void createLesson() {
        //given
        LessonDto lessonDto = createLessonDto();

        Long teacherId = createTeacher();
        Long categoryId = createCategory();

        //when
        Long lessonId = lessonService.createLesson(teacherId, categoryId, lessonDto);

        em.flush();
        em.clear();
        //then
        assertThat(teacherId).isEqualTo(lessonService.findLesson(lessonId).getTeacher().getId());
        assertThat(categoryId).isEqualTo(lessonService.findLesson(lessonId).getCategory().getId());

    }



    @Test
    @DisplayName("레슨 수정 테스트")
    public void updateLesson() {
        //given
        LessonDto lessonDto = createLessonDto();

        Long teacherId = createTeacher();
        Long categoryId = createCategory();


        Long lessonId = lessonService.createLesson(teacherId, categoryId, lessonDto);
        em.flush();
        em.clear();

        //when
        String title = "레슨수정";
        String content = "레슨 수정 내용";

        LessonUpdateDto lessonUpdateDto = new LessonUpdateDto();
        lessonUpdateDto.setTitle(title);
        lessonUpdateDto.setContent(content);
        lessonUpdateDto.setPrice(30000);

        Long updateLessonId = lessonService.updateLesson(lessonId, lessonUpdateDto);

        em.flush();
        em.clear();

        //then
        assertThat(lessonService.findLesson(updateLessonId).getTitle()).isEqualTo(title);
        assertThat(lessonService.findLesson(updateLessonId).getContent()).isEqualTo(content);


    }


    private static LessonDto createLessonDto() {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setTitle("레슨1");
        lessonDto.setStartDate(LocalDateTime.now());
        lessonDto.setEndDate(LocalDateTime.now());
        lessonDto.setPrice(10000);
        lessonDto.setContent("레슨내용1");
        return lessonDto;
    }

    private Long createCategory() {

        Category category = Category.builder()
                .name("카테고리1")
                .build();
        categoryRepository.create(category);
        return category.getId();
    }

    private Long createTeacher() {
        Teacher teacher = Teacher.builder()
                .name("강사1")
                .phoneNumber("111-1111")
                .introduction("강사소개1")
                .build();

        teacherRepository.save(teacher);
        return teacher.getId();

    }

}