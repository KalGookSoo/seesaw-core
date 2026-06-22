package kr.me.seesaw.repository.impl;

import kr.me.seesaw.domain.RoleMapping;
import kr.me.seesaw.repository.RoleMappingRepository;
import kr.me.seesaw.repository.jpa.JpaRoleMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class RoleMappingRepositoryImpl implements RoleMappingRepository {

    private final JpaRoleMappingRepository jpaRoleMappingRepository;

    public Collection<RoleMapping> findAllBySiteIdAndRoleNameIn(String siteId, Collection<String> roleNames) {
        return jpaRoleMappingRepository.findAllBySiteIdAndRoleNameIn(siteId, roleNames);
    }

}
