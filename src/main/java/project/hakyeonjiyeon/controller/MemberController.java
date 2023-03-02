package project.hakyeonjiyeon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.hakyeonjiyeon.domain.Member;
import project.hakyeonjiyeon.dto.MemberCreateDto;
import project.hakyeonjiyeon.dto.MemberUpdateDto;
import project.hakyeonjiyeon.exception.DuplicateMemberException;
import project.hakyeonjiyeon.repository.MemberRepository;
import project.hakyeonjiyeon.security.CustomUser;
import project.hakyeonjiyeon.security.SessionUser;
import project.hakyeonjiyeon.service.MemberService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    private final MemberRepository memberRepository;

    private final HttpSession httpSession;

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
            model.addAttribute("errorMessage", "이미 사용중인 아이디입니다.");
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
    public String loginMember() {
        return "member/memberLoginForm";
    }


    /*
     * 로그인 에러: 추후 수정요
     */
    @GetMapping("/login/error")
    public String loginError(Model model) {

        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "member/memberLoginForm";
    }

    /*
     * 회원정보 수정폼
     */
    @GetMapping("/detail")
    public String memberUpdateForm(Model model, Authentication authentication) {

        //로그인멤버
        SessionUser member = (SessionUser) httpSession.getAttribute("member");
        Member findMember = null;
        if (member != null) {
            findMember = memberRepository.findByEmail(member.getEmail()).get();
        }else if (authentication != null) {
            CustomUser userDetails = (CustomUser) authentication.getPrincipal();
            findMember = memberRepository.findByAuthId(userDetails.getAuthId()).get();
        }

        MemberUpdateDto memberUpdateDto = new MemberUpdateDto();
        memberUpdateDto.setName(findMember.getName());
        memberUpdateDto.setAuthId(findMember.getAuthId());
        memberUpdateDto.setNickName(findMember.getNickName());
        memberUpdateDto.setEmail(findMember.getEmail());
        memberUpdateDto.setPhoneNumber(findMember.getPhoneNumber());
        memberUpdateDto.setAddress(findMember.getAddress());

        model.addAttribute("memberUpdateDto", memberUpdateDto);

        return "member/memberUpdateForm";
    }

    /*
     * 회원정보 수정
     */
    @PostMapping("/update")
    public String memberUpdate(@Valid @ModelAttribute MemberUpdateDto memberUpdateDto, BindingResult bindingResult, Model model) {
        //validation!!!
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "member/memberUpdateForm";
        }

        try {
            memberService.update(memberUpdateDto);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberUpdateForm";
        }

        return "redirect:/order/orderList";
    }
}
