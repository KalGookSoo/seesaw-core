package kr.me.seesaw.core.domain.permission.persistence;

import kr.me.seesaw.core.domain.permission.Permission;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface JpaPermissionRepository extends Repository<Permission, String> {

    Permission save(Permission permission);

    Optional<Permission> findById(String id);

    void deleteById(String id);

    List<Permission> findAllByRoleId(String roleId);

    List<Permission> findAllByTargetId(String targetId);

    Optional<Permission> findByRoleIdAndTargetId(String roleId, String targetId);

}
