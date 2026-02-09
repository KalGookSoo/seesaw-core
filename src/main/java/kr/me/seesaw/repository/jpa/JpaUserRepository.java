package kr.me.seesaw.repository.jpa;

import kr.me.seesaw.domain.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface JpaUserRepository extends Repository<User, String> {

    User save(User user);

    Optional<User> findByUsername(String username);

}
