package kr.me.seesaw.repository.jpa;

import kr.me.seesaw.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface JpaUserPrincipalRepository extends Repository<User, String> {

    @EntityGraph(attributePaths = {"roleMappings"})
    Optional<User> findByUsername(String username);

}
