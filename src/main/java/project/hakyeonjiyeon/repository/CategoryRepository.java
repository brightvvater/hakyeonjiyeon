package project.hakyeonjiyeon.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.hakyeonjiyeon.domain.Category;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final EntityManager em;

    public void create(Category category) {
        em.persist(category);
    }

    public Category findById(Long categoryId) {
        return em.find(Category.class, categoryId);
    }

    public List<Category> findAll() {
        return em.createQuery(
                "select c from Category c"
        ).getResultList();
    }
}
