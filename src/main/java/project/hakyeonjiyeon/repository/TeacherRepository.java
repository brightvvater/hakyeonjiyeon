package project.hakyeonjiyeon.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.hakyeonjiyeon.domain.Category;
import project.hakyeonjiyeon.domain.Teacher;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TeacherRepository {

    private final EntityManager em;

    public void save(Teacher teacher) {
        em.persist(teacher);
    }

    public Teacher findById(Long teacherId) {
        return  em.find(Teacher.class, teacherId);
    }

    public List<Teacher> findAll() {
        return em.createQuery(
                "select t from Teacher t"
        ).getResultList();
    }


    public void delete(Teacher teacher) {
        em.remove(teacher);
    }


}
