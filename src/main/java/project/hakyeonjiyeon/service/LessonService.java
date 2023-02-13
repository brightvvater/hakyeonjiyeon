package project.hakyeonjiyeon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hakyeonjiyeon.domain.Category;
import project.hakyeonjiyeon.domain.Lesson;
import project.hakyeonjiyeon.domain.Teacher;
import project.hakyeonjiyeon.dto.LessonDetailDto;
import project.hakyeonjiyeon.dto.LessonDto;
import project.hakyeonjiyeon.dto.LessonMainDto;
import project.hakyeonjiyeon.dto.LessonUpdateDto;
import project.hakyeonjiyeon.repository.CategoryRepository;
import project.hakyeonjiyeon.repository.LessonRepository;
import project.hakyeonjiyeon.repository.TeacherRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
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


    //레슨만 조회
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


    //메인페이지 레슨 및 강사 조회
    public List<LessonMainDto> findLessonWithTeacherAndCategory() {
        List<Lesson> lessonWithTeacherAndCategoryList = lessonRepository.findLessonWithTeacherAndCategory();

       /* for (Lesson lesson : lessonWithTeacherAndCategoryList) {
            log.info("lesson.categoryName={}", lesson.getCategory().getName());
            log.info("lesson.teacherName={}", lesson.getTeacher().getName());
            log.info("lesson.title={}", lesson.getTitle());
        }*/

        List<LessonMainDto> list = new ArrayList<>();

        for (Lesson lesson : lessonWithTeacherAndCategoryList) {
            LessonMainDto lessonMainDto = new LessonMainDto(); //내부에서 초기화 할것!!
            lessonMainDto.setTitle(lesson.getTitle());
            lessonMainDto.setCategoryName(lesson.getCategory().getName());
            lessonMainDto.setTeacherName(lesson.getTeacher().getName());
            lessonMainDto.setLessonId(lesson.getId());
            list.add(lessonMainDto);
        }

        /*for (LessonMainDto mainDto : list) {
            log.info("lessonDto.categoryName={}", mainDto.getCategoryName());
            log.info("lessonDto.teacherName={}", mainDto.getTeacherName());
            log.info("lessonDto.title={}", mainDto.getTitle());

        }*/
        return  list;
    }

    //레슨 상세조회 페이지 조회
    public LessonDetailDto findLessonDetail(Long lessonId) {
        Lesson lessonDetail = lessonRepository.findLessonDetail(lessonId);
        LessonDetailDto lessonDetailDto = new LessonDetailDto();
        lessonDetailDto.setLessonId(lessonDetail.getId());
        lessonDetailDto.setPrice(lessonDetail.getPrice());
        lessonDetailDto.setTitle(lessonDetail.getTitle());
        lessonDetailDto.setStartDate(lessonDetail.getStartDate());
        lessonDetailDto.setEndDate(lessonDetail.getEndDate());
        lessonDetailDto.setCategoryName(lessonDetail.getCategory().getName());
        lessonDetailDto.setTeacherName(lessonDetail.getTeacher().getName());
        lessonDetailDto.setContent(lessonDetail.getContent());
        lessonDetailDto.setIntroduction(lessonDetail.getTeacher().getIntroduction());

        return lessonDetailDto;
    }



}
