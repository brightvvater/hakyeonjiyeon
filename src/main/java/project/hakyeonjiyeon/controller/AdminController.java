package project.hakyeonjiyeon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.hakyeonjiyeon.dto.*;
import project.hakyeonjiyeon.service.CategoryService;
import project.hakyeonjiyeon.service.LessonService;
import project.hakyeonjiyeon.service.TeacherService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final TeacherService teacherService;

    private final CategoryService categoryService;

    private final LessonService lessonService;

    /*
     * 강사등록폼
     */
    @GetMapping("addTeacher")
    public String addTeacherForm(Model model) {
        TeacherCreateDto teacherCreateDto = new TeacherCreateDto();
        model.addAttribute("teacherForm", teacherCreateDto);
        return "teacher/addTeacherForm";

    }

    /*
     * 강사등록
     */
    @PostMapping("addTeacher")
    public String addTeacher(@ModelAttribute TeacherCreateDto teacherCreateDto) {
        //validation!!

        Long teacherId = teacherService.addTeacher(teacherCreateDto);
        return "redirect:/";
    }


    /*
     * 카테고리등록폼
     */
    @GetMapping("addCategory")
    public String addCategoryForm(Model model) {
        CategoryCreateDto categoryCreateDto = new CategoryCreateDto();
        model.addAttribute("categoryForm", categoryCreateDto);
        return "category/addCategoryForm";

    }

    /*
     * 카테고리등록
     */
    @PostMapping("addCategory")
    public String addCategory(@ModelAttribute CategoryCreateDto categoryCreateDto) {
        //validation!!

        Long categoryId = categoryService.addCategory(categoryCreateDto);
        return "redirect:/";
    }


    /*
     * 레슨등록폼
     */
    @GetMapping("addLesson")
    public String addLessonForm(Model model) {
        LessonCreateDto lessonCreateDto = new LessonCreateDto();

       //강사조회
        List<TeacherFormDto> teacherList = teacherService.getTeacherList();
        lessonCreateDto.setTeacherList(teacherList);

        //카테고리 조회
        List<CategoryFormDto> categoryList = categoryService.getCategoryList();
        lessonCreateDto.setCategoryList(categoryList);


        model.addAttribute("lessonForm", lessonCreateDto);

        return "lesson/addLessonForm";

    }


    /*
     * 레슨등록
     */
    @PostMapping("addLesson")
    public String addLesson(@ModelAttribute LessonCreateDto lessonCreateDto) {
        //validation!!

        Long teacherId = lessonCreateDto.getTeacherId();
        Long categoryId = lessonCreateDto.getCategoryId();

        Long lessonId = lessonService.createLesson(teacherId, categoryId, lessonCreateDto);
        return "redirect:/";
    }
}
