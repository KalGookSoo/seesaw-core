package kr.me.seesaw.core.authentication;

import kr.me.seesaw.domain.vo.RoleName;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.UUID;

public class AnonymousPrincipalProvider implements PrincipalProvider {

    @Override
    public Authentication getAuthentication() {
        return new AnonymousAuthenticationToken(
                UUID.randomUUID().toString(),
                User.withUsername("anonymous").password("temp-password").build(),
                AuthorityUtils.createAuthorityList(RoleName.ROLE_ANONYMOUS.name())
        );
    }

}
