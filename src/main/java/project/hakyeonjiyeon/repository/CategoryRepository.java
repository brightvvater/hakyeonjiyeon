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

    public Long create(Category category) {
        em.persist(category);
        return category.getId();
    }

    public Category findById(Long categoryId) {
        return em.find(Category.class, categoryId);
    }

    public List<Category> findAll() {
        return em.createQuery(
                "select c from Category c"
        ).getResultList();
    }

    public List<Category> findWithLesson(Long categoryId) {
        return em.createQuery(
                "select c from Category c"
                +" where id= :id"
        ).setParameter("id", categoryId)
                .getResultList();
    }

    public void delete(Long categoryId) {
        em.createQuery(
                "delete from Category c where id= :id"
        ).setParameter("id", categoryId)
                .executeUpdate();
    }
}
