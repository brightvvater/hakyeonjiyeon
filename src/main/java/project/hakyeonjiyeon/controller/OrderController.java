package project.hakyeonjiyeon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.hakyeonjiyeon.domain.Lesson;
import project.hakyeonjiyeon.domain.Member;
import project.hakyeonjiyeon.repository.MemberRepository;
import project.hakyeonjiyeon.service.LessonService;
import project.hakyeonjiyeon.service.MemberService;
import project.hakyeonjiyeon.service.OrderService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    private final LessonService lessonService;

    private final MemberRepository memberRepository;

    @GetMapping
    public String OrderForm(@RequestParam("lessonId") Long lessonId, Model model) { //dto로 변환해서 전해주기!!!!!!!

        Lesson lesson = lessonService.findLesson(lessonId);
        model.addAttribute("lesson", lesson);
        log.info("lesson.getTitle={}", lesson.getTitle());

        //member 가져오기
        //insert into member(member_id, ADDRESS, name, nick_name, phone_number, password) values(1, '123', '조현수','brightvvater', '010-9256-5814','123123');
        Long memberId = 1L;
        Optional<Member> member = memberRepository.findById(memberId);
        model.addAttribute("member",member.get());
        log.info("member.getNickName={}", member.get().getNickName());
        return "/order/orderForm";
    }

}
