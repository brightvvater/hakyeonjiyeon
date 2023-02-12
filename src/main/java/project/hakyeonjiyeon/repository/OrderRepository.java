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

    public List<Order> findByMember(Member member) {
        return em.createQuery(
                "select o from Order o where o.member=:member")
                .setParameter("member", member)
                .getResultList();

        //"select o from Order o fetch join Member m where m.id= :memberId" jpql... ㅠㅠ
    }


}
