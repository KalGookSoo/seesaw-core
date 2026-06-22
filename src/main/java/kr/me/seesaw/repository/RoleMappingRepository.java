package kr.me.seesaw.repository;

import kr.me.seesaw.domain.RoleMapping;

import java.util.Collection;

public interface RoleMappingRepository {

    Collection<RoleMapping> findAllBySiteIdAndRoleNameIn(String siteId, Collection<String> roleNames);

}
