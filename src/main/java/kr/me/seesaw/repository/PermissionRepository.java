package kr.me.seesaw.repository;

import kr.me.seesaw.domain.Permission;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends Repository<Permission, String> {

    Permission save(Permission permission);

    List<Permission> findAllByRoleId(String roleId);

    List<Permission> findAllByTargetId(String targetId);

    Optional<Permission> findByRoleIdAndTargetId(String roleId, String targetId);

}
