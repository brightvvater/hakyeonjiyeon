package project.hakyeonjiyeon.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

     ROLE_MEMBER("ROLE_MEMBER","일반 사용자"),
     ROLE_ADMIN("ROLE_ADMIN","관리자");

     private final String key;
     private final String title;


}
