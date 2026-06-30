package kr.me.seesaw.core.domain.role.persistence;

import kr.me.seesaw.core.domain.role.Role;
import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface JpaRoleRepository extends Repository<Role, String> {

    Role save(Role role);

    List<Role> findAll();

    List<Role> findAllByIdIn(Collection<String> ids);

    Optional<Role> findByName(String name);

}
