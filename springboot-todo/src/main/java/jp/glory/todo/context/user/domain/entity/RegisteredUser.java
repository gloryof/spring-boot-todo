package jp.glory.todo.context.user.domain.entity;

import jp.glory.todo.context.user.domain.value.LoginId;
import jp.glory.todo.context.user.domain.value.Password;
import jp.glory.todo.context.user.domain.value.UserId;
import jp.glory.todo.context.user.domain.value.UserName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 登録済みユーザ.<br>
 * このオブジェクトは登録されたユーザのみ生成される。
 * 
 * @author Junki Yamada
 */
@RequiredArgsConstructor
public class RegisteredUser {

    /** ラベル. */
    public static final String LABEL = "登録済みユーザ";

    /** ユーザID. */
    @Getter
    private final UserId userId;

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