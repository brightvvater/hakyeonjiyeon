package project.hakyeonjiyeon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hakyeonjiyeon.domain.Category;
import project.hakyeonjiyeon.domain.Lesson;
import project.hakyeonjiyeon.domain.Teacher;
import project.hakyeonjiyeon.dto.LessonDto;
import project.hakyeonjiyeon.dto.LessonUpdateDto;
import project.hakyeonjiyeon.repository.CategoryRepository;
import project.hakyeonjiyeon.repository.LessonRepository;
import project.hakyeonjiyeon.repository.TeacherRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LessonService {

    private final TeacherRepository teacherRepository;
    private final CategoryRepository categoryRepository;
    private final LessonRepository lessonRepository;

    //레슨 등록
    @Transactional
    public Long createLesson(Long teacherId, Long categoryId, LessonDto lessonDto) {
        //강사조회
        Teacher teacher = teacherRepository.findById(teacherId);

        //카테고리조회
        Category category = categoryRepository.findById(categoryId);

        //레슨저장
        Lesson lesson = Lesson.builder()
                .title(lessonDto.getTitle())
                .price(lessonDto.getPrice())
                .startDate(lessonDto.getStartDate())
                .endDate(lessonDto.getEndDate())
                .teacher(teacher)
                .category(category)
                .content(lessonDto.getContent())
                .build();
        lessonRepository.save(lesson);
        return lesson.getId();
    }

    //레슨 수정
    @Transactional
    public Long updateLesson(Long lessonId, LessonUpdateDto lessonUpdateDto) {
        //레슨 조회
        Lesson findLesson = lessonRepository.findById(lessonId);

        //레슨 수정
        Long updateLessonId = findLesson.updateLesson(lessonUpdateDto);
        return updateLessonId;
    }


    //레슨 조회
    public Lesson findLesson(Long lessonId) {
        return lessonRepository.findById(lessonId);
    }

    //레슨 전체 조회
    public List<Lesson> findAllLesson() {
        return lessonRepository.findAllLesson();
    }

    //레슨 삭제
    public void deleteLesson(Long lessonId) {
        lessonRepository.deleteLesson(lessonId);
    }
}
