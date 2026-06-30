package kr.me.seesaw.core.domain.todo.persistence;

import kr.me.seesaw.core.domain.todo.VTodo;
import kr.me.seesaw.core.domain.todo.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepository {

    private final JpaVTodoRepository jpaVTodoRepository;

    @Override
    public VTodo save(VTodo todo) {
        return jpaVTodoRepository.save(todo);
    }

    @Override
    public void delete(VTodo todo) {
        jpaVTodoRepository.delete(todo);
    }

    @Override
    public Optional<VTodo> findById(String id) {
        return jpaVTodoRepository.findById(id);
    }

    @Override
    public VTodo getReferenceById(String id) {
        return jpaVTodoRepository.getReferenceById(id);
    }

}
