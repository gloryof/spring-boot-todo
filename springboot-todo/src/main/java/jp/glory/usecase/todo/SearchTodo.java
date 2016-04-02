package jp.glory.usecase.todo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import jp.glory.domain.todo.entity.Todo;
import jp.glory.domain.todo.entity.Todos;
import jp.glory.domain.todo.repository.TodoRepository;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.domain.user.value.UserId;

/**
 * TODOの検索.
 * 
 * @author Junki Yamada
 *
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
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
