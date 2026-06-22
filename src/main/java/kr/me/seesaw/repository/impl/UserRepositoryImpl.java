package kr.me.seesaw.repository.impl;

import kr.me.seesaw.domain.User;
import kr.me.seesaw.repository.UserRepository;
import kr.me.seesaw.repository.jpa.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public User save(User user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaUserRepository.findByUsername(username);
    }

    @Override
    public Collection<User> findAllByIdIn(Collection<String> ids) {
        return jpaUserRepository.findAllByIdIn(ids);
    }

}
