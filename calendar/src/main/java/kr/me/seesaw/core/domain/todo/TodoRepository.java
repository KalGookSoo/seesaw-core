package kr.me.seesaw.core.domain.todo;

import java.util.Optional;

public interface TodoRepository {

    VTodo save(VTodo todo);

    void delete(VTodo todo);

    Optional<VTodo> findById(String id);

    VTodo getReferenceById(String id);

}
