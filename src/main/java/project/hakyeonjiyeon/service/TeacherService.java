package project.hakyeonjiyeon.service;

import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.hakyeonjiyeon.domain.Category;
import project.hakyeonjiyeon.domain.MainStatus;
import project.hakyeonjiyeon.domain.Teacher;
import project.hakyeonjiyeon.domain.TeacherFile;
import project.hakyeonjiyeon.dto.CategoryCreateDto;
import project.hakyeonjiyeon.dto.TeacherCreateDto;
import project.hakyeonjiyeon.dto.TeacherFormDto;
import project.hakyeonjiyeon.repository.FileRepository;
import project.hakyeonjiyeon.repository.TeacherRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TeacherService {

    private final TeacherRepository teacherRepository;

    private final FileUploadService fileUploadService;


    //강사등록
    @Transactional
    public Long addTeacher(TeacherCreateDto teacherCreateDto) throws IOException {

        List<MultipartFile> multipartFiles = teacherCreateDto.getTeacherFiles();
        //파일 업로드
        List<TeacherFile> teacherFiles = fileUploadService.uploadTeacherFiles(multipartFiles);

        //강사 db저장
        Teacher teacher = Teacher.builder()
                .name(teacherCreateDto.getName())
                .phoneNumber(teacherCreateDto.getPhoneNumber())
                .introduction(teacherCreateDto.getIntroduction())
                .teacherFiles(teacherFiles) /////수정?
                .build();

        teacherRepository.save(teacher);
        return teacher.getId();
    }

    //전체 강사조회
    public List<TeacherFormDto> getTeacherList() {
        List<Teacher> teacherList = teacherRepository.findAll();

        List<TeacherFormDto> list = new ArrayList<>();
        for (Teacher teacher : teacherList) {
            TeacherFormDto teacherFormDto = new TeacherFormDto();
            teacherFormDto.setTeacherId(teacher.getId());
            teacherFormDto.setName(teacher.getName());

            list.add(teacherFormDto);
        }
        return list;

        /*return teacherList.stream()
                .map(t ->)
                .collect(Collectors.toList());*/

    }

    //강사 1명 조회
    public TeacherCreateDto getTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId);

        TeacherCreateDto teacherCreateDto = new TeacherCreateDto();
        teacherCreateDto.setTeacherId(teacher.getId());
        teacherCreateDto.setName(teacher.getName());
        teacherCreateDto.setPhoneNumber(teacher.getPhoneNumber());
        teacherCreateDto.setIntroduction(teacher.getIntroduction());

        List<TeacherFile> teacherFiles = teacher.getTeacherFiles();
        List<String> teacherFileList = new ArrayList<>();
        for (TeacherFile teacherFile : teacherFiles) {
            teacherFileList.add(teacherFile.getStoreFileName());
        }
        teacherCreateDto.setTeacherFileList(teacherFileList);

        return  teacherCreateDto;
    }

    //카테고리 수정
    /*@Transactional
    public void update(CategoryCreateDto categoryCreateDto) {
        Category category = categoryRepository.findById(categoryCreateDto.getCategoryId());

        category.update(categoryCreateDto);
    }*/

    //강사 삭제
    @Transactional
    public void remove(Long teacherId) throws SQLException {
        Teacher teacher = teacherRepository.findById(teacherId);

        if(!teacher.getLessons().isEmpty()){
            throw new SQLException();
        }
        teacherRepository.delete(teacher);
    }

}
