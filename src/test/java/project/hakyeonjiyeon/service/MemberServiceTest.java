package project.hakyeonjiyeon.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.hakyeonjiyeon.domain.Member;
import project.hakyeonjiyeon.dto.MemberCreateDto;
import project.hakyeonjiyeon.dto.MemberUpdateDto;
import project.hakyeonjiyeon.exception.DuplicateMemberException;
import project.hakyeonjiyeon.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("정상 회원가입 테스트")
    public void joinMember() {

        //given
        MemberCreateDto MemberCreateDto = new MemberCreateDto("member1", "멤버1", "010-111-1111", "금천구", "123123");
        Long joinMemberId = memberService.join(MemberCreateDto);

        //when
        Member findMember = memberRepository.findById(joinMemberId).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다"));
        //then
        Assertions.assertThat(joinMemberId).isEqualTo(findMember.getId());


    }

    @Test
    @DisplayName("중복 회원가입 테스트")
    public void duplicateJoinMember() {
        //given
        MemberCreateDto MemberCreateDto = new MemberCreateDto("member1", "멤버1", "010-111-1111", "금천구", "123123");
        MemberCreateDto MemberCreateDto1 = new MemberCreateDto("member1", "aa", "010-111-2222", "강남구", "123123");
        MemberCreateDto MemberCreateDto2 = new MemberCreateDto("member1", "abc", "010-123-123", "금천구", "1111");
        memberService.join(MemberCreateDto);


        //then
        Assertions.assertThatThrownBy(() -> memberService.join(MemberCreateDto)).isInstanceOf(DuplicateMemberException.class);
        Assertions.assertThatThrownBy(() -> memberService.join(MemberCreateDto1)).isInstanceOf(DuplicateMemberException.class);
//        Assertions.assertThatThrownBy(() -> memberService.join(MemberCreateDto2)).isInstanceOf(DuplicateMemberException.class);
    }


    @Test
    @DisplayName("회원 수정 테스트")
    public void updateMember() {
        //given
        MemberCreateDto MemberCreateDto = new MemberCreateDto("member1", "멤버1", "010-111-1111", "금천구", "123123");
        Long joinMemberId = memberService.join(MemberCreateDto);

        MemberUpdateDto memberUpdateDto = new MemberUpdateDto();
        memberUpdateDto.setNickName("닉네임1");
        memberUpdateDto.setAddress("강남구");

        //when
        Long updateId = memberService.update(joinMemberId, memberUpdateDto);

        //then
        Assertions.assertThat(memberRepository.findById(joinMemberId).get().getId()).isEqualTo(updateId);

    }
}