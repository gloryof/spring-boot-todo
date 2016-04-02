package jp.glory.infra.db.todo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import jp.glory.domain.todo.entity.Todo;
import jp.glory.domain.todo.entity.Todos;
import jp.glory.domain.todo.repository.TodoRepository;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.domain.user.value.UserId;

/**
 * TODOリポジトリ.
 * @author Junki Yamada
 *
 */
@Repository
public class TodoRepositoryDbImpl implements TodoRepository {

    private static final Map<Long, Todo> todos = new HashMap<>();

    private static long sequence;

    @Override
    public Optional<Todo> findBy(TodoId todoId) {

        return todos.entrySet().stream()
                    .map(e -> e.getValue())
                    .filter(v -> v.getId().isSame(todoId))
                    .findAny();
    }

    @Override
    public Todos findTodosBy(UserId userId) {

        final List<Todo> todoList = todos.entrySet().stream()
                                        .map(e -> e.getValue())
                                        .filter(v -> v.getUserId().isSame(userId))
                                        .collect(Collectors.toList());
        return new Todos(todoList);
    }

    @Override
    public TodoId save(Todo todo) {

        final long todoIdValue;
        if (!todo.getId().isSetValue()) {

            todoIdValue = sequence;
            sequence++;
        } else {

            todoIdValue = todo.getId().getValue();
        }

        return new TodoId(todoIdValue);
    }

}
