package project.hakyeonjiyeon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hakyeonjiyeon.domain.Member;
import project.hakyeonjiyeon.dto.MemberCreateDto;
import project.hakyeonjiyeon.dto.MemberUpdateDto;
import project.hakyeonjiyeon.exception.DuplicateMemberException;
import project.hakyeonjiyeon.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public Long join(MemberCreateDto memberCreateDto) {
        Member member = Member.createMember(memberCreateDto, passwordEncoder);
        validationDuplicateMember(member);

        //log.info("role={}", member.getRole());
        //log.info("roleName={}", member.getRole().name());
        memberRepository.save(member);
        return  member.getId();

    }


    ///중복회원검사
    private void validationDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMember = memberRepository.findByAuthId(member.getAuthId());
        if (!findMember.isEmpty()) {
            throw new DuplicateMemberException("이미 존재하는 회원입니다");
        }
    }

    //회원정보수정
    @Transactional
    public Long update(Long memberId, MemberUpdateDto memberUpdateDto) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        return findMember.updateMember(memberUpdateDto);
    }

    //회원탈퇴


    //로그인
    @Override
    public UserDetails loadUserByUsername(String authId) throws UsernameNotFoundException {
        List<Member> members = memberRepository.findByAuthId(authId);
        if (members.isEmpty()) {
            throw new UsernameNotFoundException(authId);
        }

        return User.builder()
                .username(members.get(0).getName())
                .password(members.get(0).getPassword())
                .authorities(members.get(0).getRole().toString())
                .build();

    }
}
