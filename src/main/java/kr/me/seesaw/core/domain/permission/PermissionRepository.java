package kr.me.seesaw.core.domain.permission;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository {

    Permission save(Permission permission);

    Optional<Permission> findById(String id);

    void deleteById(String id);

    List<Permission> findAllByRoleId(String roleId);

    List<Permission> findAllByTargetId(String targetId);

    Optional<Permission> findByRoleIdAndTargetId(String roleId, String targetId);

}
