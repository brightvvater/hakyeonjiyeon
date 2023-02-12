package project.hakyeonjiyeon.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.hakyeonjiyeon.dto.MemberUpdateDto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String nickName;

    private String password;

    private String phoneNumber;

    private String address;

    private void setId(Long id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setNickName(String nickName) {
        this.nickName = nickName;
    }

    private void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    @Builder
    public Member(String name, String nickName, String phoneNumber, String address, String password) {
        this.name = name;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
    }

    public Long updateMember(MemberUpdateDto memberUpdateDto) {
        this.setNickName(memberUpdateDto.getNickName());
        this.setPhoneNumber(memberUpdateDto.getPhoneNumber());
        this.setAddress(memberUpdateDto.getAddress());

        return this.getId();
    }


}
