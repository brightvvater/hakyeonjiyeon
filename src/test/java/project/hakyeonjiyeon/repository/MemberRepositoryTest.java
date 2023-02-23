package project.hakyeonjiyeon.repository;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.hakyeonjiyeon.domain.Grade;
import project.hakyeonjiyeon.domain.Member;
import project.hakyeonjiyeon.dto.MemberUpdateDto;


import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;


    @Test
    @DisplayName("회원가입 테스트")
    public void saveMember() {

        //given
        Member member = new Member("member1", "member1", "서울시 금천구", "010-111-1111", "123123", Grade.MEMBER);
        memberRepository.save(member);

        //then
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        assertThat(findMember).isEqualTo(member);

    }

    @Test
    @DisplayName("회원수정 테스트")
    public void updateMember() {
        //given
        Member member = new Member("member1", "member1", "서울시 금천구", "010-111-1111", "123123", Grade.MEMBER);
        memberRepository.save(member);

        em.flush();
        em.clear();

        //when
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        MemberUpdateDto dto = new MemberUpdateDto("updateMember", "010-111-2222", "서울시 강남구","123123");
        Long updateMemberId = findMember.updateMember(dto);

        //then
        assertThat(member.getId()).isEqualTo(updateMemberId);

    }


}