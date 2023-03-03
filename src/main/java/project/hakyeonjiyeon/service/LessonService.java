package project.hakyeonjiyeon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.hakyeonjiyeon.domain.*;
import project.hakyeonjiyeon.dto.*;
import project.hakyeonjiyeon.repository.CategoryRepository;
import project.hakyeonjiyeon.repository.LessonRepository;
import project.hakyeonjiyeon.repository.TeacherRepository;

import java.io.IOException;
import java.sql.SQLException;
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

    private final FileUploadService fileUploadService;



    //레슨 등록
    @Transactional
    public Long createLesson(Long teacherId, Long categoryId, LessonCreateDto lessonCreateDto) throws IOException {

        List<MultipartFile> multipartFiles = lessonCreateDto.getLessonFiles();
        //파일 업로드
        List<LessonFile> lessonFiles = fileUploadService.uploadLessonFiles(multipartFiles);

        //강사조회
        Teacher teacher = teacherRepository.findById(teacherId);

        //카테고리조회
        Category category = categoryRepository.findById(categoryId);

        //레슨저장
        Lesson lesson = Lesson.builder()
                .title(lessonCreateDto.getTitle())
                .price(lessonCreateDto.getPrice())
                .startDate(lessonCreateDto.getStartDate())
                .endDate(lessonCreateDto.getEndDate())
                .teacher(teacher)
                .category(category)
                .content(lessonCreateDto.getContent())
                .lessonFiles(lessonFiles)
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




    //레슨 삭제
    @Transactional
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
            for (LessonFile lessonFile : lesson.getLessonFiles()) {
                lessonMainDto.setImageName(lessonFile.getStoreFileName());
            }
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
        for (LessonFile lessonFile : lessonDetail.getLessonFiles()) {
            lessonDetailDto.setLessonImage(lessonFile.getStoreFileName());
        }
        for (TeacherFile teacherFile: lessonDetail.getTeacher().getTeacherFiles()) {
            lessonDetailDto.setTeacherImage(teacherFile.getStoreFileName());
        }

        return lessonDetailDto;
    }

    //레슨 삭제
    @Transactional
    public void remove(Long lessonId) {

        Lesson lesson = lessonRepository.findById(lessonId);
        lessonRepository.delete(lesson);
    }



}
