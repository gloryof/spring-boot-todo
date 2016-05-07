package jp.glory.infra.db.todo.dao;

import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.glory.infra.db.todo.entity.TodosDetailTable;

/**
 * todos_detailテーブルのDAO.
 * @author Junki Yamada
 *
 */
@ConfigAutowireable
@Dao
public interface TodosDetailDao {

    /**
     * TODOのIDをキーに検索する.
     * @param todoId TODOのID
     * @return TODO詳細レコード
     */
    @Select
    Optional<TodosDetailTable> selectById(final long todoId);

    /**
     * TODOの詳細をINSERTする.
     * @param record レコード
     * @return 登録件数
     */
    @Insert
    int insert(final TodosDetailTable record);

    /**
     * TODOの詳細を更新する.
     * @param record レコード
     * @return 更新件数
     */
    @Update
    int update(final TodosDetailTable record);

    /**
     * TODOの詳細を削除する.
     * @param record レコード
     * @return 削除件数
     */
    @Delete
    int delete(final TodosDetailTable record);
}
