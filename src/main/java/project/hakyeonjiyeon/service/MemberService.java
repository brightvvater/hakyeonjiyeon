package project.hakyeonjiyeon.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hakyeonjiyeon.domain.Member;
import project.hakyeonjiyeon.dto.MemberCreateDto;
import project.hakyeonjiyeon.dto.MemberUpdateDto;
import project.hakyeonjiyeon.exception.DuplicateMemberException;
import project.hakyeonjiyeon.repository.MemberRepository;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    public Long join(MemberCreateDto memberCreateDto) {
        Member member = new Member(memberCreateDto.getName(), memberCreateDto.getNickName(), memberCreateDto.getPhoneNumber(), memberCreateDto.getAddress(), memberCreateDto.getPassword(), memberCreateDto.getGrade());
        validationDuplicateMember(member);
        memberRepository.save(member);
        return  member.getId();

    }

    private void validationDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        for (Member findMember : findMembers) {
            if (findMember.getPassword().equals(member.getPassword())) {
                throw new DuplicateMemberException("이미 존재하는 회원입니다");
            }
        }
    }

    //회원정보수정
    @Transactional
    public Long update(Long memberId, MemberUpdateDto memberUpdateDto) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        return findMember.updateMember(memberUpdateDto);
    }

    //회원탈퇴



}
