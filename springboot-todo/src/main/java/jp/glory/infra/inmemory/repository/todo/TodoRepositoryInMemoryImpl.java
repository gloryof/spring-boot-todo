package jp.glory.infra.inmemory.repository.todo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import jp.glory.domain.todo.entity.Todo;
import jp.glory.domain.todo.entity.Todos;
import jp.glory.domain.todo.repository.TodoRepository;
import jp.glory.domain.todo.value.Memo;
import jp.glory.domain.todo.value.Summary;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.domain.user.value.UserId;
import jp.glory.infra.inmemory.repository.annotation.InMemoryRepository;

/**
 * インメモリで保持するTodoリポジトリ.
 * @author Junki Yamada
 *
 */
@InMemoryRepository
public class TodoRepositoryInMemoryImpl implements TodoRepository {

    /**
     * TODOのMap.
     */
    private final Map<Long, Todo> todoMap = new HashMap<>();

    /**
     * シーケンス.
     */
    private long sequence = 1;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Todo> findBy(TodoId todoId) {

        return Optional
                .ofNullable(todoMap.get(todoId.getValue()))
                .map(this::copy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Todos findTodosBy(UserId userId) {

        final List<Todo> todoList = todoMap.entrySet()
                .stream()
                .filter(e -> e.getValue().getUserId().isSame(userId))
                .sorted((v1, v2) -> v1.getKey() < v2.getKey() ? -1 : 1)
                .map(v -> copy(v.getValue()))
                .collect(Collectors.toList());

        return new Todos(todoList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TodoId save(Todo todo) {

        final Todo saveTodo;
        if (!todo.isRegistered()) {

            saveTodo = new Todo(new TodoId(getSequence()), todo.getUserId(), todo.getSummary(), todo.getMemo(),
                    todo.isCompleted());
            saveTodo.version(1l);
            incrementSequence();

        } else {

            final Todo beforeTodo = findBy(todo.getId())
                                        .orElseThrow(() ->new IllegalStateException("更新対象が存在しません"));

            final long nextVersion = beforeTodo.getEntityVersion() + 1;
            saveTodo = copy(todo);
            saveTodo.version(nextVersion);
        }
        todoMap.put(saveTodo.getId().getValue(), saveTodo);

        return saveTodo.getId();
    }

    /**
     * 内容をコピーする.
     * @param src 元オブジェクト
     * @return コピーオブジェクト
     */
    private Todo copy(final Todo src) {

        return new Todo(
                new TodoId(src.getId().getValue()),
                new UserId(src.getUserId().getValue()),
                new Summary(src.getSummary().getValue()),
                new Memo(src.getMemo().getValue()),
                src.isCompleted());
    }

    /**
     * シーケンス取得.
     * @return シーケンス
     */
    protected long getSequence() {
        return sequence;
    }

    /**
     * シーケンスの増加.<br>
     * 継承して拡張するための拡張ポイント。
     */
    protected void incrementSequence() {

        sequence++;
    }
}
