package kr.me.seesaw.repository;

import kr.me.seesaw.domain.User;

import java.util.Optional;

/**
 * 계정 저장소
 */
public interface UserRepository {

    User save(User user);

    Optional<User> findByUsername(String username);

}
