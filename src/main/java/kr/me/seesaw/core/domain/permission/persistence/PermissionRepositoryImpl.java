package kr.me.seesaw.core.domain.permission.persistence;

import kr.me.seesaw.core.domain.permission.Permission;
import kr.me.seesaw.core.domain.permission.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PermissionRepositoryImpl implements PermissionRepository {

    private final JpaPermissionRepository jpaPermissionRepository;

    @Override
    public Permission save(Permission permission) {
        return jpaPermissionRepository.save(permission);
    }

    @Override
    public Optional<Permission> findById(String id) {
        return jpaPermissionRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        jpaPermissionRepository.deleteById(id);
    }

    @Override
    public List<Permission> findAllByRoleId(String roleId) {
        return jpaPermissionRepository.findAllByRoleId(roleId);
    }

    @Override
    public List<Permission> findAllByTargetId(String targetId) {
        return jpaPermissionRepository.findAllByTargetId(targetId);
    }

    @Override
    public Optional<Permission> findByRoleIdAndTargetId(String roleId, String targetId) {
        return jpaPermissionRepository.findByRoleIdAndTargetId(roleId, targetId);
    }

}
