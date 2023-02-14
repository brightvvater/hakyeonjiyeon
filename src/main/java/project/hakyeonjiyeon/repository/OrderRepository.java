package project.hakyeonjiyeon.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.hakyeonjiyeon.domain.Member;
import project.hakyeonjiyeon.domain.Order;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public Long save(Order order) {
        em.persist(order);
        return order.getId();
    }

    public Order findById(Long orderId) {
        return em.find(Order.class, orderId);
    }

    public List<Order> findAll() {
        return  em.createQuery(
                "select o from Order o"
        ).getResultList();
    }

    public List<Order> findByMember(Long memberId) {
        return em.createQuery(
                "select o from Order o join fetch o.lesson where o.member.id=:memberId")
                .setParameter("memberId", memberId)
                .getResultList();

    }


}
