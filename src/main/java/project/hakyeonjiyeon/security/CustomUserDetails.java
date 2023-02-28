package project.hakyeonjiyeon.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.hakyeonjiyeon.domain.Role;
import project.hakyeonjiyeon.dto.MemberCreateDto;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean accountNonLocked=true;
    private boolean accountNonExpired=true;

    private boolean credentialsNonExpired =true;
    private boolean enabled=true;

    private String authId;
    private String email;


    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(MemberCreateDto memberCreateDto) {
        this.username = memberCreateDto.getName();
        this.password = memberCreateDto.getPassword();
        this.authId = memberCreateDto.getAuthId();
        this.email = memberCreateDto.getEmail();
        Collection<GrantedAuthority> roles =
                Arrays.stream(memberCreateDto.getRole().split(","))
                        .map(role -> new SimpleGrantedAuthority(username))
                        .collect(Collectors.toList());
        this.authorities = roles;
    }




}
