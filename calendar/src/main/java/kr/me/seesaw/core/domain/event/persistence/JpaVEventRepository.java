package kr.me.seesaw.core.domain.event.persistence;

import kr.me.seesaw.core.domain.event.VEvent;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface JpaVEventRepository extends Repository<VEvent, String> {

    VEvent save(VEvent event);

    void delete(VEvent event);

    Optional<VEvent> findById(String id);

    VEvent getReferenceById(String id);

    VEvent saveAndFlush(VEvent event);

}
