package project.hakyeonjiyeon.controller;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import project.hakyeonjiyeon.domain.Lesson;
import project.hakyeonjiyeon.dto.LessonDetailDto;
import project.hakyeonjiyeon.dto.LessonMainDto;
import project.hakyeonjiyeon.service.LessonService;

@Controller
@RequiredArgsConstructor
@RequestMapping("lesson")
@Slf4j
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("/detail/{lessonId}")
    public String lessonDetail(@PathVariable("lessonId") Long lessonId, Model model) {
        LessonDetailDto lessonDetail = lessonService.findLessonDetail(lessonId);
        model.addAttribute("lesson", lessonDetail);

        log.info("lessonImage={}", lessonDetail.getLessonImage());
        log.info("teacherImage={}", lessonDetail.getTeacherImage());
        return "lesson/detail";
    }
}
