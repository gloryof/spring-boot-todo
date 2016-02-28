package jp.glory.usecase.todo;

import jp.glory.domain.todo.entity.Todos;
import jp.glory.domain.todo.repository.TodoRepository;
import jp.glory.domain.user.value.UserId;

/**
 * TODOの検索.
 * 
 * @author Junki Yamada
 *
 */
public class SearchTodo {

    private final TodoRepository repository;

    /**
     * コンストラクタ.
     * 
     * @param repository
     *            リポジトリ
     */
    public SearchTodo(final TodoRepository repository) {

        this.repository = repository;
    }

    /**
     * ユーザIDをキーに検索する.
     * 
     * @param userId
     *            ユーザID
     * @return Todoリスト
     */
    protected Todos searchByUser(UserId userId) {

        return repository.findBy(userId);
    }

}
