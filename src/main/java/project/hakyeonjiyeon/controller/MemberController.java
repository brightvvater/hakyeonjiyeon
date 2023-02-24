package project.hakyeonjiyeon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.h2.engine.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.hakyeonjiyeon.dto.MemberCreateDto;
import project.hakyeonjiyeon.exception.DuplicateMemberException;
import project.hakyeonjiyeon.service.MemberService;

import javax.naming.Binding;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    /*
     * 회원가입폼
     */
    @GetMapping("/new")
    public String addMemberForm(Model model) {
        model.addAttribute("memberCreateDto", new MemberCreateDto());
        return "member/addMemberForm";
    }

    /*
     * 회원가입
     */
    @PostMapping("/new")
    public String addMember(@Valid @ModelAttribute MemberCreateDto memberCreateDto, BindingResult bindingResult, Model model) {
        //validation!!
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "member/addMemberForm";
        }

        try {
            Long memberId = memberService.join(memberCreateDto);
        } catch (DuplicateMemberException e) {
            model.addAttribute("errorMessage", e.getMessage()); //추후 아이디 중복검사로 빼기
            return "member/addMemberForm";
        }


        return "redirect:/";
    }

    /*
     * 로그인폼
     */
    @GetMapping("/login")
    public String loginMember() {
        return "member/memberLoginForm";
    }


    /*
     * 로그인 에러
     */
    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "member/memberLoginForm";
    }
}
