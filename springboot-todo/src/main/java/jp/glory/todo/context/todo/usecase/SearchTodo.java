package jp.glory.todo.context.todo.usecase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import jp.glory.todo.context.base.usecase.Usecase;
import jp.glory.todo.context.todo.domain.entity.Todo;
import jp.glory.todo.context.todo.domain.entity.Todos;
import jp.glory.todo.context.todo.domain.repository.TodoRepository;
import jp.glory.todo.context.todo.domain.value.TodoId;
import jp.glory.todo.context.user.domain.value.UserId;

/**
 * TODOの検索.
 * 
 * @author Junki Yamada
 *
 */
@Usecase
public class SearchTodo {

    /**
     * リポジトリ.
     */
    private final TodoRepository repository;

    /**
     * コンストラクタ.
     * 
     * @param repository
     *            リポジトリ
     */
    @Autowired
    public SearchTodo(final TodoRepository repository) {

        this.repository = repository;
    }

    /**
     * TODOのIDをキーに検索する
     * @param todoId TODOのID
     * @return TODO情報
     */
    public Optional<Todo> searchById(TodoId todoId) {

        return repository.findBy(todoId);
    }

    /**
     * ユーザIDをキーに検索する.
     * 
     * @param userId
     *            ユーザID
     * @return Todoリスト
     */
    public Todos searchTodosByUser(UserId userId) {

        return repository.findTodosBy(userId);
    }

}
