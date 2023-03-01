package project.hakyeonjiyeon.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.hakyeonjiyeon.dto.MemberCreateDto;
import project.hakyeonjiyeon.dto.MemberUpdateDto;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String authId;

    private String name;

    private String nickName;

    private String password;

    private String phoneNumber;

    private String address;

    private String email;

    private String picture;

    @Enumerated(EnumType.STRING)
    private Role role;

    private void setId(Long id) {
        this.id = id;
    }

    private void setAuthId(String authId) {
        this.authId = authId;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setNickName(String nickName) {
        this.nickName = nickName;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private void setRole(Role role) {
        this.role = role;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    @Builder
    public Member(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }



    public static Member createMember(MemberCreateDto memberCreateDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setAuthId(memberCreateDto.getAuthId());
        member.setName(memberCreateDto.getName());
        member.setNickName(memberCreateDto.getNickName());
        member.setAddress(memberCreateDto.getAddress());
        member.setPhoneNumber(memberCreateDto.getPhoneNumber());
        member.setRole(Role.ROLE_ADMIN);
        member.setEmail(memberCreateDto.getEmail());
        String password = passwordEncoder.encode(memberCreateDto.getPassword());
        member.setPassword(password);

        return member;
    }

    public Member update(String name, String picture) {
        this.name= name;
        this.picture =picture;

        return  this;
    }

    public Long updateMember(MemberUpdateDto memberUpdateDto) {
        this.setNickName(memberUpdateDto.getNickName());
        this.setPhoneNumber(memberUpdateDto.getPhoneNumber());
        this.setAddress(memberUpdateDto.getAddress());
        this.setPassword(memberUpdateDto.getPassword());

        return this.getId();
    }


}
