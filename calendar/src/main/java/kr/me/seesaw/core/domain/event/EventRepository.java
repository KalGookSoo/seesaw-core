package kr.me.seesaw.core.domain.event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository {

    VEvent save(VEvent event);

    VEvent getReferenceById(String id);

    Optional<VEvent> findById(String id);

    List<VEvent> findAll(String categoryId, LocalDateTime start, LocalDateTime end, String query);

    void delete(VEvent event);

    VEvent update(VEvent event);

}
