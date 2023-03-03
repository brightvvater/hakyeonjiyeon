package project.hakyeonjiyeon.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.hakyeonjiyeon.domain.Lesson;
import project.hakyeonjiyeon.dto.LessonMainDto;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LessonRepository {

    private final EntityManager em;

    public void save(Lesson lesson) {
        em.persist(lesson);

    }

    public Lesson findById(Long lessonId) {
        return em.find(Lesson.class, lessonId);
    }

    public List<Lesson> findAllLesson() {
        return em.createQuery("select l from Lesson l").getResultList();
    }

    public void deleteLesson(Long lessonId) {
        em.createQuery(
                        "delete from Lesson l where l.id = :lessonId"
                ).setParameter("lessonId", lessonId)
                .executeUpdate();
    }

    public List<Lesson> findLessonWithTeacherAndCategory() {
        return em.createQuery(
                "select l from Lesson l" +
                        " join fetch l.teacher" +
                        " join fetch l.category"
        ).getResultList();
    }

    public Lesson findLessonDetail(Long lessonId) {
        return (Lesson) em.createQuery(
                "select l from Lesson l" +
                        " join fetch l.teacher" +
                        " join fetch l.category"+
                        " where l.id= :id"
        ).setParameter("id",lessonId)
                .getSingleResult();
    }

    public void delete(Lesson lesson) {
        em.remove(lesson);
    }


}
