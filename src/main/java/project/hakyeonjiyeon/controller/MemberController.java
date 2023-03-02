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
import project.hakyeonjiyeon.domain.Member;
import project.hakyeonjiyeon.dto.LoginFormDto;
import project.hakyeonjiyeon.dto.MemberCreateDto;
import project.hakyeonjiyeon.exception.DuplicateMemberException;
import project.hakyeonjiyeon.repository.MemberRepository;
import project.hakyeonjiyeon.service.MemberService;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    private final MemberRepository memberRepository;

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
            model.addAttribute("errorMessage", e.getMessage());
            return "member/addMemberForm";
        }

        return "redirect:/";
    }

    /*
     * 아이디 중복검사
     */
    @PostMapping("/duplicate")
    public String duplicateValidation(@ModelAttribute MemberCreateDto memberCreateDto, Model model) {

        if (memberCreateDto.getAuthId().isEmpty()) {
            model.addAttribute("errorMessage", "아이디를 입력해주세요.");
            return "member/addMemberForm";
        }
        Optional<Member> member = memberRepository.findByAuthId(memberCreateDto.getAuthId());

        if (!member.isEmpty()) {
            model.addAttribute("errorMessage","이미 사용중인 아이디입니다.");
            return "member/addMemberForm";
        }

        model.addAttribute("clear", "사용 가능한 아이디 입니다.");
        return "/member/addMemberForm";
    }

    /*
     * 로그인메인 페이지
     */
    @GetMapping("/loginMain")
    public String loginMain() {
        return "member/loginMain";
    }

    /*
     * 로그인폼
     */
    @GetMapping("/login")
    public String loginMember(Model model) {
        model.addAttribute("loginFormDto", new LoginFormDto());
        return "member/memberLoginForm";
    }


    /*
     * 로그인 에러
     */
    @GetMapping("/login/error")
    public String loginError(@ModelAttribute LoginFormDto loginFormDto, Model model) {
        log.info("loginForm={}", loginFormDto.getAuthId());

        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "member/memberLoginForm";
    }
}
