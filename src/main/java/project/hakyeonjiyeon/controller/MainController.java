package project.hakyeonjiyeon.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.hakyeonjiyeon.dto.LessonMainDto;
import project.hakyeonjiyeon.service.LessonService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final LessonService lessonService;

    //메인페이지
    @GetMapping("/")
    public String main(Model model) {
        List<LessonMainDto> lessons = lessonService.findLessonWithTeacherAndCategory();
        model.addAttribute("lessons", lessons);
        return "index";
    }
}
