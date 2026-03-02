package kr.me.seesaw.repository;

import kr.me.seesaw.domain.VEvent;
import kr.me.seesaw.dto.query.EventQuery;

import java.util.List;
import java.util.Optional;

public interface EventRepository {

    VEvent save(VEvent event);

    VEvent getReferenceById(String id);

    Optional<VEvent> findById(String id);

    List<VEvent> findAll(EventQuery eventQuery);

    void delete(VEvent event);

}
