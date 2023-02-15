package project.hakyeonjiyeon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hakyeonjiyeon.domain.Lesson;
import project.hakyeonjiyeon.domain.Member;
import project.hakyeonjiyeon.domain.Order;
import project.hakyeonjiyeon.domain.OrderStatus;
import project.hakyeonjiyeon.dto.MyPageFormDto;
import project.hakyeonjiyeon.dto.OrderCreateDto;
import project.hakyeonjiyeon.dto.OrderFormDto;
import project.hakyeonjiyeon.repository.LessonRepository;
import project.hakyeonjiyeon.repository.MemberRepository;
import project.hakyeonjiyeon.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    private final MemberRepository memberRepository;

    private final LessonRepository lessonRepository;



    //주문 생성
    @Transactional
    public Long createOrder(Long memberId, Long lessonId, OrderCreateDto orderCreateDto) {
        //회원 조회
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        //레슨 조회
        Lesson lesson = lessonRepository.findById(lessonId);
        //주문
        Order order = Order.builder()
                .orderDate(orderCreateDto.getOrderDate())
                .orderStatus(OrderStatus.ORDER)
                .lesson(lesson)
                .member(member)
                .payMethod(orderCreateDto.getPayMethod())
                .build();

        return orderRepository.save(order);
    }

    //주문 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId);
        order.cancelOrder();

    }


    //주문 form에 member, lesson 데이터 전달
    public OrderFormDto showMemberAndLesson(Long lessonId, Long memberId) {
        Lesson lesson = lessonRepository.findById(lessonId);
        Member member = memberRepository.findById(memberId).get();

        OrderFormDto orderFormDto = new OrderFormDto();
        orderFormDto.setLessonId(lessonId);
        orderFormDto.setMemberId(memberId);
        orderFormDto.setName(member.getName());
        orderFormDto.setTitle(lesson.getTitle());
        orderFormDto.setPhoneNumber(member.getPhoneNumber());
        orderFormDto.setPrice(lesson.getPrice());

        return orderFormDto;
    }

    //회원 id로 주문내역 확인
    public List<MyPageFormDto> selectOrderList(Long memberId) {
        List<Order> orderList = orderRepository.findByMember(memberId);


        List<MyPageFormDto> list = new ArrayList<>();
        for (Order order : orderList) {
            MyPageFormDto myPageFormDto = new MyPageFormDto();
            myPageFormDto.setTitle(order.getLesson().getTitle());
            myPageFormDto.setPrice(order.getLesson().getPrice());
            myPageFormDto.setTeacherName(order.getLesson().getTeacher().getName());
            list.add(myPageFormDto);
        }

        return list;

    }

}
