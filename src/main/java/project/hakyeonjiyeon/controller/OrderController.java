package project.hakyeonjiyeon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.hakyeonjiyeon.domain.Lesson;
import project.hakyeonjiyeon.domain.Member;
import project.hakyeonjiyeon.domain.PayMethod;
import project.hakyeonjiyeon.dto.MyPageFormDto;
import project.hakyeonjiyeon.dto.OrderCreateDto;
import project.hakyeonjiyeon.dto.OrderFormDto;
import project.hakyeonjiyeon.repository.MemberRepository;
import project.hakyeonjiyeon.service.LessonService;
import project.hakyeonjiyeon.service.MemberService;
import project.hakyeonjiyeon.service.OrderService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    private final MemberRepository memberRepository;


    @GetMapping
    public String OrderForm(@RequestParam("lessonId") Long lessonId,
                            Model model, Authentication authentication) {

        //로그인 멤버
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //log.info("userDetails={}", userDetails);
        //log.info("id={}", memberRepository.findIdByUserName(userDetails.getUsername()));
        Long memberId = memberRepository.findIdByUserName(userDetails.getUsername()).get().getId();


        OrderFormDto orderFormDto = orderService.showMemberAndLesson(lessonId, memberId);

        //log.info("orderFormDto.payMethod={}", orderFormDto.getPayMethod());
        model.addAttribute("orderForm", orderFormDto);
        model.addAttribute("payMethod", PayMethod.values());

        return "order/orderForm";
    }

    @PostMapping
    public String order(@Valid @ModelAttribute OrderCreateDto orderCreateDto, BindingResult bindingResult) {
        //validation!!!
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
        }

        orderCreateDto.setOrderDate(LocalDateTime.now());
        //log.info("orderCreateDto.payMethod={}", orderCreateDto.getPayMethod());
        orderService.createOrder(orderCreateDto.getMemberId(),orderCreateDto.getLessonId(),orderCreateDto);
        return "redirect:/";
    }

    @GetMapping("/orderList")
    public String getOrderList(Model model, Authentication authentication) {

        //로그인멤버
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //log.info("userDetails={}", userDetails);
        //log.info("id={}", memberRepository.findIdByUserName(userDetails.getUsername()));
        Long memberId = memberRepository.findIdByUserName(userDetails.getUsername()).get().getId();

        List<MyPageFormDto> myPageFormDtos = orderService.selectOrderList(memberId);
        model.addAttribute("myPageFormList", myPageFormDtos);

        //member와 order간 다대일 조인으로 member도 List로 반환됨, memberRepository를 통해 따로 조회함
        MyPageFormDto myPageFormDto = new MyPageFormDto();
        myPageFormDto.setMemberId(memberId);
        myPageFormDto.setMemberName(memberRepository.findById(memberId).get().getName());
        model.addAttribute("myPageForm", myPageFormDto);

        return "myPage/myPage";
    }
}
