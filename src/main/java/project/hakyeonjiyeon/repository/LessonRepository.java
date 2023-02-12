package project.hakyeonjiyeon.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.hakyeonjiyeon.domain.Lesson;

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


}
