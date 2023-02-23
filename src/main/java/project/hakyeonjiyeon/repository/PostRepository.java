package project.hakyeonjiyeon.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.hakyeonjiyeon.domain.Post;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public Long save(Post post) {
        em.persist(post);
        return post.getId();
    }

    public Post findById(Long postId) {
        return em.find(Post.class, postId);
    }

    public List<Post> findAll() {
        return em.createQuery(
                "select p from Post p"
                + " join fetch p.member"
                + " join fetch p.board"
        ).getResultList();
    }

    public Post findByIdWithMember(Long postId) {
        return (Post) em.createQuery(
                "select p from Post p"
                +" join fetch p.member"
                +" join fetch p.board"
                +" where p.id=:id"
        ).setParameter("id",postId)
                .getSingleResult();
    }
}
