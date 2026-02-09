package kr.me.seesaw.repository;

import kr.me.seesaw.domain.VTodo;

import java.util.Optional;

public interface VTodoRepository {

    VTodo save(VTodo todo);

    void delete(VTodo todo);

    Optional<VTodo> findById(String id);

    VTodo getReferenceById(String id);

}
