package jp.glory.todo.context.user.domain.repository;

import java.util.List;
import java.util.Optional;

import jp.glory.todo.context.user.domain.entity.User;
import jp.glory.todo.context.user.domain.value.LoginId;
import jp.glory.todo.context.user.domain.value.UserId;

/**
 * ユーザリポジトリ.
 *
 * @author Junki Yamada
 */
public interface UserRepository {

    /**
     * 全てのユーザを取得する.
     *
     * @return ユーザリスト
     */
    List<User> findAll();

    /**
     * ユーザを保存する.
     *
     * @param user
     *            ユーザ
     * @return 保存したユーザID
     */
    UserId save(final User user);

    /**
     * ユーザIDでユーザを探す.
     *
     * @param userId
     *            ユーザID
     * @return ユーザ
     */
    Optional<User> findBy(final UserId userId);

    /**
     * ログインIDでユーザを探す.
     *
     * @param loginId
     *            ログインID
     * @return ユーザ
     */
    Optional<User> findBy(final LoginId loginId);
}