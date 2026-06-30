package kr.me.seesaw.core.domain.role;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RoleRepository {

    Role save(Role role);

    List<Role> findAll();

    List<Role> findAllByIdIn(Collection<String> ids);

    Optional<Role> findByName(String name);

}
