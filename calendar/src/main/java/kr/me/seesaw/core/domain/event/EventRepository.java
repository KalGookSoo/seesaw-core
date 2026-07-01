package kr.me.seesaw.core.domain.event;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository {

    VEvent save(VEvent event);

    VEvent getReferenceById(String id);

    Optional<VEvent> findById(String id);

    List<VEvent> findAll(String categoryId, @Nullable LocalDateTime start, @Nullable LocalDateTime end, @Nullable String query);

    void delete(VEvent event);

    VEvent update(VEvent event);

}
