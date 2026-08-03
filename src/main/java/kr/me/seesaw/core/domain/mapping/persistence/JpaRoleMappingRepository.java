package kr.me.seesaw.core.domain.mapping.persistence;

import kr.me.seesaw.core.domain.mapping.RoleMapping;
import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.Optional;

public interface JpaRoleMappingRepository extends Repository<RoleMapping, String> {

    Collection<RoleMapping> findAllBySiteIdAndRoleNameIn(String siteId, Collection<String> roleNames);

    Optional<RoleMapping> findByUserIdAndSiteId(String userId, String siteId);

    void deleteByUserIdAndSiteId(String userId, String siteId);

}
