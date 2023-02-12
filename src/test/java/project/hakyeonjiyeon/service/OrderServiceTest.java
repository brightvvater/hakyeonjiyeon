package project.hakyeonjiyeon.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.hakyeonjiyeon.domain.Category;
import project.hakyeonjiyeon.domain.Order;
import project.hakyeonjiyeon.domain.OrderStatus;
import project.hakyeonjiyeon.domain.Teacher;
import project.hakyeonjiyeon.dto.LessonDto;
import project.hakyeonjiyeon.dto.MemberDto;
import project.hakyeonjiyeon.dto.OrderDto;
import project.hakyeonjiyeon.repository.CategoryRepository;
import project.hakyeonjiyeon.repository.OrderRepository;
import project.hakyeonjiyeon.repository.TeacherRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private OrderService orderService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    @DisplayName("주문 생성 테스트")
    public void createOrder() {
        //given
        //회원 생성
        Long memberId = createMember();
        //레슨 생성
        Long lessonId = createLesson();

        //when
        OrderDto orderDto = new OrderDto(LocalDateTime.now());
        //주문 생성
        Long orderId = orderService.createOrder(memberId, lessonId, orderDto);

        em.flush();
        em.clear();
        //then
        assertThat(orderRepository.findById(orderId).getMember().getId()).isEqualTo(memberId);
        assertThat(orderRepository.findById(orderId).getLesson().getId()).isEqualTo(lessonId);

    }

    @Test
    @DisplayName("주문 취소 테스트")
    public void cancelOrder() {
        //given
        //회원 생성
        Long memberId = createMember();
        //레슨 생성
        Long lessonId = createLesson();
        //주문 생성
        OrderDto orderDto = new OrderDto(LocalDateTime.now());
        Long orderId = orderService.createOrder(memberId, lessonId, orderDto);

        //when
        orderService.cancelOrder(orderId);

        //then
        assertThat(orderRepository.findById(orderId).getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(orderRepository.findById(orderId).getCancelDate()).isNotNull();
    }

    @Test
    @DisplayName("회원의 주문 리스트 조회 테스트")
    public void orderList() {
        //given
        //회원 생성
        Long memberId = createMember();
        //레슨 생성
        Long lessonId = createLesson();
        //주문 생성
        OrderDto orderDto = new OrderDto(LocalDateTime.now());
        Long orderId = orderService.createOrder(memberId, lessonId, orderDto);
        Long orderId2 = orderService.createOrder(memberId, lessonId, orderDto);

        //when
        List<Order> orderByMember = orderService.findOrderByMember(memberId);
        //then
        assertThat(orderByMember.size()).isEqualTo(2);

    }

    private Long createLesson() {
        LessonDto lessonDto = createLessonDto();

        Long teacherId = createTeacher();
        Long categoryId = createCategory();

        Long lessonId = lessonService.createLesson(teacherId, categoryId, lessonDto);
        return lessonId;
    }

    private Long createMember() {
        MemberDto memberDto = new MemberDto("member1", "멤버1", "010-111-1111", "금천구", "123123");
        Long joinMemberId = memberService.join(memberDto);
        return joinMemberId;

    }


    private static LessonDto createLessonDto() {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setTitle("레슨1");
        lessonDto.setStartDate(LocalDateTime.now());
        lessonDto.setEndDate(LocalDateTime.now());
        lessonDto.setPrice(10000);
        lessonDto.setContent("레슨내용1");
        return lessonDto;
    }

    private Long createCategory() {

        Category category = Category.builder()
                .name("카테고리1")
                .build();
        categoryRepository.create(category);
        return category.getId();
    }

    private Long createTeacher() {
        Teacher teacher = Teacher.builder()
                .name("강사1")
                .phoneNumber("111-1111")
                .introduction("강사소개1")
                .build();

        teacherRepository.save(teacher);
        return teacher.getId();

    }
}