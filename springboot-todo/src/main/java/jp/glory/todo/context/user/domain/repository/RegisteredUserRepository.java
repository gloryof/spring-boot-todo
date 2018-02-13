package jp.glory.todo.context.user.domain.repository;

import java.util.List;
import java.util.Optional;

import jp.glory.todo.context.user.domain.entity.RegisteredUser;
import jp.glory.todo.context.user.domain.value.LoginId;
import jp.glory.todo.context.user.domain.value.UserId;

/**
 * 登録済みユーザリポジトリ.
 *
 * @author Junki Yamada
 */
public interface RegisteredUserRepository {

    /**
     * 全てのユーザを取得する.
     *
     * @return ユーザリスト
     */
    List<RegisteredUser> findAll();

    /**
     * ユーザを保存する.
     *
     * @param user ユーザ
     */
    void save(final RegisteredUser user);

    /**
     * ユーザIDでユーザを探す.
     *
     * @param userId
     *            ユーザID
     * @return ユーザ
     */
    Optional<RegisteredUser> findBy(final UserId userId);

    /**
     * ログインIDでユーザを探す.
     *
     * @param loginId
     *            ログインID
     * @return ユーザ
     */
    Optional<RegisteredUser> findBy(final LoginId loginId);
}