package project.hakyeonjiyeon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hakyeonjiyeon.domain.Teacher;
import project.hakyeonjiyeon.dto.TeacherCreateDto;
import project.hakyeonjiyeon.dto.TeacherFormDto;
import project.hakyeonjiyeon.repository.TeacherRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    //강사등록
    @Transactional
    public Long addTeacher(TeacherCreateDto teacherCreateDto) {

        Teacher teacher = Teacher.builder()
                .name(teacherCreateDto.getName())
                .phoneNumber(teacherCreateDto.getPhoneNumber())
                .introduction(teacherCreateDto.getIntroduction())
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

}
