package kr.me.seesaw.core.domain.mapping;

import java.util.Collection;

public interface RoleMappingRepository {

    Collection<RoleMapping> findAllBySiteIdAndRoleNameIn(String siteId, Collection<String> roleNames);

}
