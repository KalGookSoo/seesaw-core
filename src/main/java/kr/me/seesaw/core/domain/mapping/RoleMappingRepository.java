package kr.me.seesaw.core.domain.mapping;

import java.util.Collection;
import java.util.Optional;

public interface RoleMappingRepository {

    Collection<RoleMapping> findAllBySiteIdAndRoleNameIn(String siteId, Collection<String> roleNames);

    Optional<RoleMapping> findByUserIdAndSiteId(String userId, String siteId);

    void deleteByUserIdAndSiteId(String userId, String siteId);

}
