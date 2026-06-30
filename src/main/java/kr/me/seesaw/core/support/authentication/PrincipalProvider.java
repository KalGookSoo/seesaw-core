package kr.me.seesaw.core.support.authentication;

import org.springframework.security.core.Authentication;

public interface PrincipalProvider {

    Authentication getAuthentication();

}
