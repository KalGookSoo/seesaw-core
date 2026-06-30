package kr.me.seesaw.core.domain.user.persistence;

import kr.me.seesaw.core.domain.user.User;
import kr.me.seesaw.core.domain.user.UserPrincipalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserPrincipalRepositoryImpl implements UserPrincipalRepository {

    private final JpaUserPrincipalRepository jpaUserPrincipalRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaUserPrincipalRepository.findByUsername(username);
    }

}
