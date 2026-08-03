package kr.me.seesaw.core.domain.mapping.persistence;

import kr.me.seesaw.core.domain.mapping.RoleMapping;
import kr.me.seesaw.core.domain.mapping.RoleMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleMappingRepositoryImpl implements RoleMappingRepository {

    private final JpaRoleMappingRepository jpaRoleMappingRepository;

    public Collection<RoleMapping> findAllBySiteIdAndRoleNameIn(String siteId, Collection<String> roleNames) {
        return jpaRoleMappingRepository.findAllBySiteIdAndRoleNameIn(siteId, roleNames);
    }

    public Optional<RoleMapping> findByUserIdAndSiteId(String userId, String siteId) {
        return jpaRoleMappingRepository.findByUserIdAndSiteId(userId, siteId);
    }

    public void deleteByUserIdAndSiteId(String userId, String siteId) {
        jpaRoleMappingRepository.deleteByUserIdAndSiteId(userId, siteId);
    }

}
