package project.hakyeonjiyeon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.h2.engine.Mode;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.hakyeonjiyeon.domain.Category;
import project.hakyeonjiyeon.domain.TeacherFile;
import project.hakyeonjiyeon.dto.*;
import project.hakyeonjiyeon.repository.CategoryRepository;
import project.hakyeonjiyeon.service.*;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    private final TeacherService teacherService;

    private final CategoryService categoryService;

    private final LessonService lessonService;

    private final BoardService boardService;


    /*
     * 강사등록폼
     */
    @GetMapping("/addTeacher")
    public String addTeacherForm(Model model) {
        TeacherCreateDto teacherCreateDto = new TeacherCreateDto();
        model.addAttribute("teacherCreateDto", teacherCreateDto);
        return "teacher/addTeacherForm";

    }

    /*
     * 강사등록
     */
    @PostMapping("/addTeacher")
    public String addTeacher(@Valid @ModelAttribute TeacherCreateDto teacherCreateDto, BindingResult bindingResult, Model model) throws IOException {

        //log.info("multipartFile={}", teacherCreateDto.getTeacherFiles().get(0).getOriginalFilename());
        //validation!!
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "teacher/addTeacherForm";
        }
        List<String> originalFile = new ArrayList<>();
        List<MultipartFile> teacherFiles= teacherCreateDto.getTeacherFiles();
        for (MultipartFile teacherFile : teacherFiles) {
            if(!teacherFile.getOriginalFilename().isBlank()){
                originalFile.add(teacherFile.getOriginalFilename());
            }
        }
        if (originalFile.isEmpty()) {
            model.addAttribute("errorMessage", "파일을 한개 이상 등록해주세요.");
            return "teacher/addTeacherForm";
        }

        Long teacherId = teacherService.addTeacher(teacherCreateDto);
        return "redirect:/";
    }

    /*
     * 강사 목록
     */
    @GetMapping("/teacher")
    public String getTeacherList(Model model) {
        List<TeacherFormDto> teacherList = teacherService.getTeacherList();
        model.addAttribute("teacherList", teacherList);
        return "teacher/teacherList";
    }

    /*
     * 강사 디테일
     */
    @GetMapping("/teacher/{teacherId}")
    public String teacherDetail(@PathVariable("teacherId") Long teacherId, Model model) {

        TeacherCreateDto teacherCreateDto = teacherService.getTeacher(teacherId);
        model.addAttribute("teacherCreateDto", teacherCreateDto);
        return "teacher/detail";
    }

    /*
     * 강사수정
     */
    @PostMapping("/teacher/{teacherId}")
    public String teacherUpdate() {
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        return "redirect:/";
    }

    /*
    * 강사 삭제
    */
    @GetMapping("/teacher/remove/{teacherId}")
    public String removeTeacher(@PathVariable("teacherId") Long teacherId, Model model) {
        try {
            teacherService.remove(teacherId);
        }catch (SQLException e) {
            model.addAttribute("errorMessage", "하위레슨이 있어 삭제할 수 없습니다.");
            List<TeacherFormDto> teacherList = teacherService.getTeacherList();
            model.addAttribute("teacherList", teacherList);
            return "teacher/teacherList";
        }

        return "redirect:/admin/teacher";
    }


    /*
     * 카테고리등록폼
     */
    @GetMapping("/addCategory")
    public String addCategoryForm(Model model) {
        CategoryCreateDto categoryCreateDto = new CategoryCreateDto();
        model.addAttribute("categoryCreateDto", categoryCreateDto);
        return "category/addCategoryForm";

    }

    /*
     * 카테고리등록
     */
    @PostMapping("/addCategory")
    public String addCategory(@Valid @ModelAttribute CategoryCreateDto categoryCreateDto, BindingResult bindingResult) {
        //validation!!
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "category/addCategoryForm";
        }

        Long categoryId = categoryService.addCategory(categoryCreateDto);
        return "redirect:/";
    }

    /*
     * 카테고리 목록
     */
    @GetMapping("/category")
    public String getCategoryList(Model model) {
        List<CategoryFormDto> categoryList = categoryService.getCategoryList();
        model.addAttribute("categoryList", categoryList);
        return "category/categoryList";
    }

    /*
     * 카테고리 디테일
     */
    @GetMapping("/category/{categoryId}")
    public String categoryDetail(@PathVariable("categoryId")Long categoryId, Model model) {

        CategoryCreateDto categoryCreateDto = categoryService.getCategory(categoryId);
        model.addAttribute("categoryCreateDto", categoryCreateDto);
        return "category/detail";
    }

    /*
     * 카테고리수정
     */
    @PostMapping("/category/{categoryId}")
    public String categoryUpdate(@PathVariable("categoryId") Long categoryId, @ModelAttribute CategoryCreateDto categoryCreateDto) {

        categoryService.update(categoryCreateDto);
        return "redirect:/";
    }

    /*
     * 카테고리 삭제
     */
    @GetMapping("/category/remove/{categoryId}")
    public String removeCategory(@PathVariable("categoryId") Long categoryId, Model model) {
        try {
            categoryService.remove(categoryId);
        }catch (SQLException e) {
            model.addAttribute("errorMessage", "하위레슨이 있어 삭제할 수 없습니다.");
            List<CategoryFormDto> categoryList = categoryService.getCategoryList();
            model.addAttribute("categoryList", categoryList);
            return "category/categoryList";
        }

        return "redirect:/admin/category";
    }


    /*
     * 레슨등록폼
     */
    @GetMapping("/addLesson")
    public String addLessonForm(Model model) {
        LessonCreateDto lessonCreateDto = new LessonCreateDto();

       //강사조회
        List<TeacherFormDto> teacherList = teacherService.getTeacherList();
        lessonCreateDto.setTeacherList(teacherList);

        //카테고리 조회
        List<CategoryFormDto> categoryList = categoryService.getCategoryList();
        lessonCreateDto.setCategoryList(categoryList);


        model.addAttribute("lessonCreateDto", lessonCreateDto);

        return "lesson/addLessonForm";

    }


    /*
     * 레슨등록
     */
    @PostMapping("/addLesson")
    public String addLesson(@Valid @ModelAttribute LessonCreateDto lessonCreateDto, BindingResult bindingResult, Model model) throws IOException {
        //validation!!
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            //강사조회
            List<TeacherFormDto> teacherList = teacherService.getTeacherList();
            lessonCreateDto.setTeacherList(teacherList);

            //카테고리 조회
            List<CategoryFormDto> categoryList = categoryService.getCategoryList();
            lessonCreateDto.setCategoryList(categoryList);

            return "lesson/addLessonForm";
        }

        List<String> originalFile = new ArrayList<>();
        List<MultipartFile> lessonFiles= lessonCreateDto.getLessonFiles();
        for (MultipartFile lessonFile : lessonFiles) {
            if(!lessonFile.getOriginalFilename().isBlank()){
                originalFile.add(lessonFile.getOriginalFilename());
            }
        }
        if (originalFile.isEmpty()) {
            model.addAttribute("errorMessage", "파일을 한개 이상 등록해주세요.");

            //강사조회
            List<TeacherFormDto> teacherList = teacherService.getTeacherList();
            lessonCreateDto.setTeacherList(teacherList);

            //카테고리 조회
            List<CategoryFormDto> categoryList = categoryService.getCategoryList();
            lessonCreateDto.setCategoryList(categoryList);
            return "lesson/addLessonForm";
        }

        Long teacherId = lessonCreateDto.getTeacherId();
        Long categoryId = lessonCreateDto.getCategoryId();

        Long lessonId = lessonService.createLesson(teacherId, categoryId, lessonCreateDto);

        return "redirect:/";
    }

    /*
     * 레슨 목록
     */
    @GetMapping("/lesson")
    public String getLessonList(Model model) {
        List<LessonMainDto> lessonList = lessonService.findLessonWithTeacherAndCategory();
        model.addAttribute("lessonList", lessonList);
        return "lesson/lessonList";
    }

    //레슨 수정폼 따로

    /*
     * 레슨수정
     */
    @PostMapping("/lesson/{lessonId}")
    public String lessonUpdate() {

        return "redirect:/";
    }

    /*
     * 레슨 삭제
     */
    @GetMapping("/lesson/remove/{lessonId}")
    public String removeLesson(@PathVariable("lessonId") Long lessonId) {

        lessonService.remove(lessonId);
        return "redirect:/admin/lesson";
    }

    /*
    * 게시판 등록폼
    */
    @GetMapping("/addBoard")
    public String addBoardForm(Model model) {
        BoardCreateDto boardCreateDto = new BoardCreateDto();
        model.addAttribute("boardCreateDto", boardCreateDto);
        return "board/addBoardForm";

    }

    /*
     * 게시판 등록
     */
    @PostMapping("/addBoard")
    public String addBoard(@Valid @ModelAttribute BoardCreateDto boardCreateDto, BindingResult bindingResult) {
        //validation!!
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "board/addBoardForm";
        }

        Long boardId = boardService.createBoard(boardCreateDto);
        return "redirect:/";
    }

    /*
     * 게시판 목록
     */
    @GetMapping("/board")
    public String getBoardList(Model model) {
        List<BoardFormDto> boardList = boardService.getBoardList();
        model.addAttribute("boardList", boardList);
        return "board/boardList";
    }

    /*
     * 게시판 삭제
     */
    @GetMapping("/board/remove/{boardId}")
    public String removeBoard(@PathVariable("boardId") Long boardId, Model model) {
        try {
            boardService.remove(boardId);
        }catch (SQLException e) {
            model.addAttribute("errorMessage", "하위 게시물이 있어 삭제할 수 없습니다.");
            List<BoardFormDto> boardList = boardService.getBoardList();
            model.addAttribute("boardList", boardList);
            return "board/boardList";
        }

        return "redirect:/admin/board";
    }


}
