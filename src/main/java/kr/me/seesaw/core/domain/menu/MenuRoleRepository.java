package kr.me.seesaw.core.domain.menu;

import kr.me.seesaw.core.domain.mapping.MenuRole;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MenuRoleRepository {

    MenuRole save(MenuRole menuRole);

    Optional<MenuRole> findById(String id);

    void deleteById(String id);

    List<MenuRole> findAllByMenuIdIn(Collection<String> menuIds);

    List<MenuRole> findAllByRoleIdIn(Collection<String> roleIds);

}
