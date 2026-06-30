package kr.me.seesaw.core.domain.mapping.persistence;

import kr.me.seesaw.core.domain.mapping.RoleMapping;
import org.springframework.data.repository.Repository;

import java.util.Collection;

public interface JpaRoleMappingRepository extends Repository<RoleMapping, String> {

    Collection<RoleMapping> findAllBySiteIdAndRoleNameIn(String siteId, Collection<String> roleNames);

}
