package project.hakyeonjiyeon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hakyeonjiyeon.domain.Category;
import project.hakyeonjiyeon.domain.Teacher;
import project.hakyeonjiyeon.dto.CategoryCreateDto;
import project.hakyeonjiyeon.dto.CategoryFormDto;
import project.hakyeonjiyeon.dto.TeacherFormDto;
import project.hakyeonjiyeon.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    //카테고리 등록
    @Transactional
    public Long addCategory(CategoryCreateDto categoryCreateDto) {
        Category category = Category.builder()
                .name(categoryCreateDto.getName())
                .build();

        categoryRepository.create(category);
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
}
