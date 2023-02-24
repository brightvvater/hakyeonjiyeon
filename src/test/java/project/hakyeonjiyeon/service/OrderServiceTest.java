package project.hakyeonjiyeon.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.hakyeonjiyeon.domain.*;
import project.hakyeonjiyeon.dto.LessonCreateDto;
import project.hakyeonjiyeon.dto.MemberCreateDto;
import project.hakyeonjiyeon.dto.OrderCreateDto;
import project.hakyeonjiyeon.repository.CategoryRepository;
import project.hakyeonjiyeon.repository.MemberRepository;
import project.hakyeonjiyeon.repository.OrderRepository;
import project.hakyeonjiyeon.repository.TeacherRepository;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
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

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("주문 생성 테스트")
    public void createOrder() throws IOException {
        //given
        //회원 생성
        Long memberId = createMember();
        //레슨 생성
        Long lessonId = createLesson();

        //when
        OrderCreateDto orderCreateDto = new OrderCreateDto();
        orderCreateDto.setOrderDate(LocalDateTime.now());
        orderCreateDto.setPayMethod(PayMethod.CARD);
        //주문 생성
        Long orderId = orderService.createOrder(memberId, lessonId, orderCreateDto);

        em.flush();
        em.clear();
        //then
        assertThat(orderRepository.findById(orderId).getMember().getId()).isEqualTo(memberId);
        assertThat(orderRepository.findById(orderId).getLesson().getId()).isEqualTo(lessonId);

    }

    @Test
    @DisplayName("주문 취소 테스트")
    public void cancelOrder() throws IOException {
        //given
        //회원 생성
        Long memberId = createMember();
        //레슨 생성
        Long lessonId = createLesson();
        //주문 생성
        OrderCreateDto orderCreateDto = new OrderCreateDto();
        orderCreateDto.setOrderDate(LocalDateTime.now());
        orderCreateDto.setPayMethod(PayMethod.CARD);
        Long orderId = orderService.createOrder(memberId, lessonId, orderCreateDto);

        //when
        orderService.cancelOrder(orderId);

        //then
        assertThat(orderRepository.findById(orderId).getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(orderRepository.findById(orderId).getCancelDate()).isNotNull();
    }

    @Test
    @DisplayName("회원의 주문 리스트 조회 테스트")
    public void orderList() throws IOException {
        //given
        //회원 생성
        Long memberId = createMember();
        //레슨 생성
        Long lessonId = createLesson();
        //주문 생성
        OrderCreateDto orderCreateDto = new OrderCreateDto();
        orderCreateDto.setOrderDate(LocalDateTime.now());
        orderCreateDto.setPayMethod(PayMethod.CARD);
        Long orderId = orderService.createOrder(memberId, lessonId, orderCreateDto);
        Long orderId2 = orderService.createOrder(memberId, lessonId, orderCreateDto);

        //when
        //Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        List<Order> orderByMember = orderRepository.findByMember(memberId);
        //then
        assertThat(orderByMember.size()).isEqualTo(2);

    }

    private Long createLesson() throws IOException {
        LessonCreateDto lessonCreateDto = createLessonDto();

        Long teacherId = createTeacher();
        Long categoryId = createCategory();

        Long lessonId = lessonService.createLesson(teacherId, categoryId, lessonCreateDto);
        return lessonId;
    }

    private Long createMember() {
        MemberCreateDto memberCreateDto = new MemberCreateDto("이름","아이디1","닉네임","010-9256-5814","주소","123","gildong@naver.com");
        Long joinMemberId = memberService.join(memberCreateDto);
        return joinMemberId;

    }


    private static LessonCreateDto createLessonDto() {
        LessonCreateDto lessonCreateDto = new LessonCreateDto();
        List<MultipartFile> list = new ArrayList<>();
        lessonCreateDto.setTitle("레슨1");
        lessonCreateDto.setStartDate(LocalDateTime.now());
        lessonCreateDto.setEndDate(LocalDateTime.now());
        lessonCreateDto.setPrice(10000);
        lessonCreateDto.setContent("레슨내용1");
        lessonCreateDto.setLessonFiles(list);
        return lessonCreateDto;
    }

    private Long createCategory() {

        Category category = Category.builder()
                .name("카테고리1")
                .build();
        categoryRepository.create(category);
        return category.getId();
    }

    private Long createTeacher() {
        List<TeacherFile> list = new ArrayList<>();
        Teacher teacher = Teacher.builder()
                .name("강사1")
                .phoneNumber("111-1111")
                .introduction("강사소개1")
                .teacherFiles(list)
                .build();

        teacherRepository.save(teacher);
        return teacher.getId();

    }
}