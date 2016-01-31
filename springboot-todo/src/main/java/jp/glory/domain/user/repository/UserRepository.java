package jp.glory.domain.user.repository;

import java.util.List;
import java.util.Optional;

import jp.glory.domain.user.entity.User;
import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.UserId;

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