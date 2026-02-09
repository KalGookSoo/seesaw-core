package kr.me.seesaw.repository.impl;

import kr.me.seesaw.domain.User;
import kr.me.seesaw.repository.UserPrincipalRepository;
import kr.me.seesaw.repository.jpa.JpaUserPrincipalRepository;
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
