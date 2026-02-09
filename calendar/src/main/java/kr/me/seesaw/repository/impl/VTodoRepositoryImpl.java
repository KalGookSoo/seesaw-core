package kr.me.seesaw.repository.impl;

import kr.me.seesaw.domain.VTodo;
import kr.me.seesaw.repository.VTodoRepository;
import kr.me.seesaw.repository.jpa.JpaVTodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VTodoRepositoryImpl implements VTodoRepository {

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
