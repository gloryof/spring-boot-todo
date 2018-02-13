package jp.glory.todo.context.user.domain.repository;

import jp.glory.todo.context.user.domain.entity.NoRegisteredUser;
import jp.glory.todo.context.user.domain.value.UserId;

/**
 * 未登録ユーザリポジトリ.
 * @author Junki Yamada
 *
 */
public interface NoRegisteredUserRepository {

    /**
     * 未登録のユーザを登録する.
     * @param user ユーザ
     * @return 登録されたユーザID
     */
    UserId save(final NoRegisteredUser user);
}
