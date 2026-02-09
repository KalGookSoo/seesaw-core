package kr.me.seesaw.repository.jpa;

import kr.me.seesaw.domain.VTodo;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface JpaVTodoRepository extends Repository<VTodo, String> {

    VTodo save(VTodo todo);

    void delete(VTodo todo);

    Optional<VTodo> findById(String id);

    VTodo getReferenceById(String id);

}
