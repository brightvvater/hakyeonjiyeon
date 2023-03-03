package project.hakyeonjiyeon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import project.hakyeonjiyeon.security.CustomUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public Long join(MemberCreateDto memberCreateDto) throws DuplicateMemberException{
        Member member = Member.createMember(memberCreateDto, passwordEncoder);
        validationDuplicateMember(member);

        //log.info("role={}", member.getRole());
        //log.info("roleName={}", member.getRole().name());
        memberRepository.save(member);
        return  member.getId();

    }


    ///중복회원검사
    private void validationDuplicateMember(Member member) throws DuplicateMemberException{
        //EXCEPTION
        Optional<Member> findMember = memberRepository.findByAuthId(member.getAuthId());
        if (!findMember.isEmpty()) {
            throw new DuplicateMemberException("이미 존재하는 회원입니다");
        }
    }

    //회원정보수정
    @Transactional
    public Long update(MemberUpdateDto memberUpdateDto) {
        Member findMember = memberRepository.findByAuthId(memberUpdateDto.getAuthId()).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        return findMember.updateMember(memberUpdateDto, passwordEncoder);
    }

    //회원탈퇴


    //로그인
    @Override
    public UserDetails loadUserByUsername(String authId) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByAuthId(authId);
        if (member.isEmpty()) {
            throw new UsernameNotFoundException(authId);
        }

        List<GrantedAuthority> role = new ArrayList();
        role.add(new SimpleGrantedAuthority(member.get().getRole().name()));
        log.info("email={}",member.get().getEmail());

        return new CustomUser(member.get().getName(), member.get().getPassword(), role, member.get().getAuthId(), member.get().getEmail());

    }



}
