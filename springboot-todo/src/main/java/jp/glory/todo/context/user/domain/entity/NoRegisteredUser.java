package jp.glory.todo.context.user.domain.entity;

import jp.glory.todo.context.user.domain.value.LoginId;
import jp.glory.todo.context.user.domain.value.Password;
import jp.glory.todo.context.user.domain.value.UserName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 未登録のユーザ.
 * @author Junki Yamada
 *
 */
@RequiredArgsConstructor
public class NoRegisteredUser {

    /** ラベル. */
    public static final String LABEL = "未登録ユーザ";

    /** ログインID. */
    @Getter
    private final LoginId loginId;

    /** ユーザ名. */
    @Getter
    private final UserName userName;

    /** パスワード. */
    @Getter
    private final Password password;
}
