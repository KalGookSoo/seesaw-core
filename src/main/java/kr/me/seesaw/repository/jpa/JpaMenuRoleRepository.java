package kr.me.seesaw.repository.jpa;

import kr.me.seesaw.domain.MenuRole;
import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface JpaMenuRoleRepository extends Repository<MenuRole, String> {

    MenuRole save(MenuRole menuRole);

    Optional<MenuRole> findById(String id);

    void deleteById(String id);

    List<MenuRole> findAllByMenuIdIn(Collection<String> menuIds);

    List<MenuRole> findAllByRoleIdIn(Collection<String> roleIds);

}
