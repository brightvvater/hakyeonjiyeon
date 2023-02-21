package project.hakyeonjiyeon.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.hakyeonjiyeon.domain.LessonFile;
import project.hakyeonjiyeon.domain.TeacherFile;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class FileRepository {

    private final EntityManager em;

    public void saveTeacher(TeacherFile teacherFile) {
        em.persist(teacherFile);
    }

    public void saveLesson(LessonFile lessonFile) {
        em.persist(lessonFile);
    }
}
