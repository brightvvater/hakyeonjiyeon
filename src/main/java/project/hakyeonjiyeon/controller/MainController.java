package project.hakyeonjiyeon.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.hakyeonjiyeon.dto.CategoryFormDto;
import project.hakyeonjiyeon.dto.LessonMainDto;
import project.hakyeonjiyeon.service.CategoryService;
import project.hakyeonjiyeon.service.LessonService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final LessonService lessonService;

    private final CategoryService categoryService;

    //메인페이지
    @GetMapping("/")
    public String main(Model model) {
        List<LessonMainDto> lessons = lessonService.findLessonWithTeacherAndCategory();

        //카테고리 넣어주기?
        LessonMainDto lessonMainDto = new LessonMainDto();
        List<CategoryFormDto> categoryList = categoryService.getCategoryList();
        lessonMainDto.setCategoryList(categoryList);

        model.addAttribute("lessons", lessons);
        model.addAttribute("categoryList", categoryList);
        return "index";
    }
}
