package jp.glory.todo.external.db.user.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.glory.todo.external.db.user.entity.UsersTable;

/**
 * usersテーブルDAO.
 * @author Junki Yamada
 *
 */
@ConfigAutowireable
@Dao
public interface UsersDao {

    /**
     * ユーザIDをキーに取得する.
     * @param userId ユーザID
     * @return ユーザ
     */
    @Select
    Optional<UsersTable> selectById(final long userId);

    /**
     * ログインIDをキーに取得する.
     * @param userId ログインID
     * @return ユーザ
     */
    @Select
    Optional<UsersTable> selectByLoginId(final String loginId);

    /**
     * ユーザを全て取得する.
     * @return ユーザリスト
     */
    @Select
    List<UsersTable> selectAll();

    /**
     * 次のユーザIDを取得する.
     * @return ユーザID
     */
    @Select
    long selectUserId();

    /**
     * ユーザの情報をinsertする.
     * @param record レコード 
     * @return 登録件数
     */
    @Insert
    int insert(final UsersTable record);

    /**
     * ユーザの情報を更新する.
     * @param record レコード
     * @return 登録件数
     */
    @Update
    int update(final UsersTable record);
}
