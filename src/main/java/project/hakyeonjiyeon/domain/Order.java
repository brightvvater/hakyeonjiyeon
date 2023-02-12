package project.hakyeonjiyeon.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private LocalDateTime orderDate;

    private LocalDateTime cancelDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Builder
    public Order(LocalDateTime orderDate, OrderStatus orderStatus, Member member, Lesson lesson) {
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.member = member;
        this.lesson = lesson;
    }

    public void cancelOrder() {
        this.setOrderStatus(OrderStatus.CANCEL);
        this.setCancelDate(LocalDateTime.now());
    }

    private void setId(Long id) {
        this.id = id;
    }

    private void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    private void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    private void setMember(Member member) {
        this.member = member;
    }

    private void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    private void setCancelDate(LocalDateTime cancelDate) {
        this.cancelDate = cancelDate;
    }
}
