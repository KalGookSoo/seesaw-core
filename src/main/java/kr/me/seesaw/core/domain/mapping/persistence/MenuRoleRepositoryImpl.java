package kr.me.seesaw.core.domain.mapping.persistence;

import kr.me.seesaw.core.domain.mapping.MenuRole;
import kr.me.seesaw.core.domain.menu.MenuRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MenuRoleRepositoryImpl implements MenuRoleRepository {

    private final JpaMenuRoleRepository jpaMenuRoleRepository;

    @Override
    public MenuRole save(MenuRole menuRole) {
        return jpaMenuRoleRepository.save(menuRole);
    }

    @Override
    public Optional<MenuRole> findById(String id) {
        return jpaMenuRoleRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        jpaMenuRoleRepository.deleteById(id);
    }

    @Override
    public List<MenuRole> findAllByMenuIdIn(Collection<String> menuIds) {
        return jpaMenuRoleRepository.findAllByMenuIdIn(menuIds);
    }

    @Override
    public List<MenuRole> findAllByRoleIdIn(Collection<String> roleIds) {
        return jpaMenuRoleRepository.findAllByRoleIdIn(roleIds);
    }

}
