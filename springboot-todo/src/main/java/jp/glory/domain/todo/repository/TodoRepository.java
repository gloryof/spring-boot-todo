package jp.glory.domain.todo.repository;

import java.util.Optional;

import jp.glory.domain.todo.entity.Todo;
import jp.glory.domain.todo.entity.Todos;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.domain.user.value.UserId;

/**
 * TODOのリポジトリ.
 * 
 * @author Junki Yamada
 */
public interface TodoRepository {

    /**
     * TODOのIDをキーにTODOを取得する.
     * @param todoId TODOのID
     * @return TODO情報
     */
    Optional<Todo> findBy(final TodoId todoId);

    /**
     * ユーザIDをキーにTODOを取得する.
     * 
     * @param userId
     *            ユーザID
     * @return TODOリスト
     */
    Todos findTodosBy(final UserId userId);

    /**
     * TODOを保存する.
     * 
     * @param todo
     *            TODOエンティティ
     * @return TODOのID
     */
    TodoId save(final Todo todo);
}
