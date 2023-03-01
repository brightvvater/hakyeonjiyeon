package project.hakyeonjiyeon.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import project.hakyeonjiyeon.dto.MemberCreateDto;

import java.util.Collection;

@Getter @Setter
public class CustomUser extends User {

    private String authId;
    private String email;
    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, String authId, String email) {
        super(username, password, authorities);
        this.authId = authId;
        this.email = email;

    }




}
