package jp.glory.infra.db.todo.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.glory.infra.db.todo.entity.TodoInfo;
import jp.glory.infra.db.todo.entity.TodosTable;

/**
 * todosテーブルDAO.
 * @author Junki Yamada
 *
 */
@ConfigAutowireable
@Dao
public interface TodosDao {

    /**
     * TODOのIDをキーに検索する.
     * @param todoId TODOのID
     * @return TODO情報
     */
    @Select
    Optional<TodoInfo> selectById(final long todoId);

    /**
     * ユーザIDをキーに検索する.
     * @param userId ユーザID
     * @return TODO情報リスト
     */
    @Select
    List<TodoInfo> selectByUserId(final long userId);

    /**
     * 次のTODO-IDを取得する.
     * @return TODOのID
     */
    @Select
    long selectTodoId();

    /**
     * TODOの情報をINSERTする.
     * @param record レコード
     * @return 登録件数
     */
    @Insert
    int insert(final TodosTable record);

    /**
     * TODOの情報を更新する.
     * @param record レコード
     * @return 更新件数
     */
    @Update
    int update(final TodosTable record);

    /**
     * TODOの情報を削除する.
     * @param record レコード
     * @return 削除件数
     */
    @Delete
    int delete(final TodosTable record);
}
