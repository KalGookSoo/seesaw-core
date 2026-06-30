package kr.me.seesaw.core.domain.todo.persistence;

import kr.me.seesaw.core.domain.todo.VTodo;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface JpaVTodoRepository extends Repository<VTodo, String> {

    VTodo save(VTodo todo);

    void delete(VTodo todo);

    Optional<VTodo> findById(String id);

    VTodo getReferenceById(String id);

}
