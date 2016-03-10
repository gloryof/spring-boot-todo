package jp.glory.domain.todo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import jp.glory.domain.todo.entity.Todo;
import jp.glory.domain.todo.entity.Todos;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.domain.user.value.UserId;

public class TodoRepositoryMock implements TodoRepository {

    private final Map<Long, Todo> todoMap = new HashMap<>();

    private long sequence = 1;

    @Override
    public Todos findTodosBy(UserId userId) {

        final List<Todo> todoList = todoMap.entrySet().stream().filter(e -> e.getValue().getUserId().isSame(userId))
                .sorted((v1, v2) -> v1.getKey() < v2.getKey() ? -1 : 1).map(v -> v.getValue())
                .collect(Collectors.toList());

        return new Todos(todoList);
    }

    @Override
    public TodoId save(final Todo todo) {

        final Todo saveTodo;
        if (!todo.isRegistered()) {

            saveTodo = new Todo(new TodoId(sequence), todo.getUserId(), todo.getSummary(), todo.getMemo(),
                    todo.isCompleted());
            sequence++;

        } else {

            saveTodo = todo;
        }
        todoMap.put(saveTodo.getId().getValue(), saveTodo);

        return saveTodo.getId();
    }

    public long getCurrentSequence() {

        return sequence;
    }

    public void setSequence(final long value) {

        sequence = value;
    }

    public Optional<Todo> getResult(final long id) {

        return Optional.ofNullable(todoMap.get(id));
    }

    @Override
    public Optional<Todo> findBy(TodoId todoId) {

        return Optional.ofNullable(todoMap.get(todoId.getValue()));
    }
}
