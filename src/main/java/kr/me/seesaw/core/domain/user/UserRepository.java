package kr.me.seesaw.core.domain.user;

import java.util.Collection;
import java.util.Optional;

/**
 * 계정 저장소
 */
public interface UserRepository {

    User save(User user);

    User getReferenceById(String id);

    Optional<User> findByUsername(String username);

    Collection<User> findAllByIdIn(Collection<String> ids);

}
