package kr.me.seesaw.core.domain.user;

import java.util.Optional;

/**
 * 계정 인증 주체 저장소
 */
public interface UserPrincipalRepository {

    /**
     * 계정명으로 계정 인증 주체를 반환합니다.
     *
     * @param username 계정명
     * @return 계정 인증 주체
     */
    Optional<User> findByUsername(String username);

}
