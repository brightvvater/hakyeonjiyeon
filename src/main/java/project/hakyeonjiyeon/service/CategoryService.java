package project.hakyeonjiyeon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hakyeonjiyeon.domain.Category;
import project.hakyeonjiyeon.domain.Member;
import project.hakyeonjiyeon.domain.Teacher;
import project.hakyeonjiyeon.dto.CategoryCreateDto;
import project.hakyeonjiyeon.dto.CategoryFormDto;
import project.hakyeonjiyeon.dto.MemberUpdateDto;
import project.hakyeonjiyeon.dto.TeacherFormDto;
import project.hakyeonjiyeon.repository.CategoryRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;


    //카테고리 등록
    @Transactional
    public Long addCategory(CategoryCreateDto categoryCreateDto) {

            Category category = Category.builder()
                    .name(categoryCreateDto.getName())
                    .build();

            Long categoryId = categoryRepository.create(category);

            return category.getId();

    }

    //전체 카테고리조회
    public List<CategoryFormDto> getCategoryList() {
        List<Category> categoryList = categoryRepository.findAll();

        List<CategoryFormDto> list = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryFormDto categoryFormDto = new CategoryFormDto();
            categoryFormDto.setCategoryId(category.getId());
            categoryFormDto.setName(category.getName());

            list.add(categoryFormDto);
        }
        return list;

        /*return teacherList.stream()
                .map(t ->)
                .collect(Collectors.toList());*/
    }

    //카테고리 1개 조회
    public CategoryCreateDto getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId);

        CategoryCreateDto categoryCreateDto = new CategoryCreateDto();
        categoryCreateDto.setName(category.getName());
        categoryCreateDto.setCategoryId(category.getId());

        return  categoryCreateDto;
    }

    //카테고리 수정
    @Transactional
    public void update(CategoryCreateDto categoryCreateDto) {
        Category category = categoryRepository.findById(categoryCreateDto.getCategoryId());

        category.update(categoryCreateDto);
    }

    @Transactional
    public void remove(Long categoryId) throws SQLException {

        List<Category> categories = categoryRepository.findWithLesson(categoryId);
        /*for (Category category : categories) {
            log.info("category={}", category.getLessons());
        }*/
        if(!categories.isEmpty()){
            throw new SQLException();
        }
        categoryRepository.delete(categoryId);
    }



}
