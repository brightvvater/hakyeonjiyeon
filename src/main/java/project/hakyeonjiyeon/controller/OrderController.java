package project.hakyeonjiyeon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.hakyeonjiyeon.domain.Lesson;
import project.hakyeonjiyeon.domain.Member;
import project.hakyeonjiyeon.domain.PayMethod;
import project.hakyeonjiyeon.dto.OrderCreateDto;
import project.hakyeonjiyeon.dto.OrderFormDto;
import project.hakyeonjiyeon.repository.MemberRepository;
import project.hakyeonjiyeon.service.LessonService;
import project.hakyeonjiyeon.service.MemberService;
import project.hakyeonjiyeon.service.OrderService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
@Slf4j
public class OrderController {

    private final OrderService orderService;


    @GetMapping
    public String OrderForm(@RequestParam("lessonId") Long lessonId,
                            @RequestParam("memberId") Long memberId,
                            Model model) {

        //로그인 멤버 아이디 넘어오도록 변경 요!!
        OrderFormDto orderFormDto = orderService.showMemberAndLesson(lessonId, memberId);

        //log.info("orderFormDto.payMethod={}", orderFormDto.getPayMethod());
        model.addAttribute("orderForm", orderFormDto);
        model.addAttribute("payMethod", PayMethod.values());

        return "/order/orderForm";
    }

    @PostMapping
    public String order(@Valid @ModelAttribute OrderCreateDto orderCreateDto, BindingResult bindingResult) {
        //validation!!!

        orderCreateDto.setOrderDate(LocalDateTime.now());
        //log.info("orderCreateDto.payMethod={}", orderCreateDto.getPayMethod());
        orderService.createOrder(orderCreateDto.getMemberId(),orderCreateDto.getLessonId(),orderCreateDto);
        return "redirect:/";
    }

    /*@GetMapping("/orderList")
    public String getOrderList() {

    }*/
}
