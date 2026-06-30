package kr.me.seesaw.core.domain.user.persistence;

import kr.me.seesaw.core.domain.user.User;
import kr.me.seesaw.core.domain.user.UserRepository;
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
    public User getReferenceById(String id) {
        return jpaUserRepository.getReferenceById(id);
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
