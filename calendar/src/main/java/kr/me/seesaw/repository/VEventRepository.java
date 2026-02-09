package kr.me.seesaw.repository;

import kr.me.seesaw.domain.VEvent;

import java.util.Optional;

public interface VEventRepository {

    VEvent save(VEvent event);

    void delete(VEvent event);

    Optional<VEvent> findById(String id);

    VEvent getReferenceById(String id);

}
