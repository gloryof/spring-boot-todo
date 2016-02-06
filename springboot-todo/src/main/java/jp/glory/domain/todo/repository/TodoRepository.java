package jp.glory.domain.todo.repository;

import java.util.List;

import jp.glory.domain.todo.entity.Todo;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.domain.user.value.UserId;

/**
 * TODOのリポジトリ.
 * @author Junki Yamada
 */
public interface TodoRepository {
    /**
     * ユーザIDをキーにTODOを取得する.
     * @param userId ユーザID
     * @return TODOリスト
     */
    List<Todo> findBy(final UserId userId);

    /**
     * TODOを保存する.
     * @param todo TODOエンティティ
     * @return TODOのID
     */
    TodoId save(final Todo todo);
}
