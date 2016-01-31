package jp.glory.domain.user.entity;

import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.Password;
import jp.glory.domain.user.value.UserId;
import jp.glory.domain.user.value.UserName;
import lombok.Getter;

/**
 * ユーザ.
 * 
 * @author Junki Yamada
 */
public class User {

    /** ラベル. */
    public static final String LABEL = "ユーザ";

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

    /**
     * コンストラクタ.
     * 
     * @param paramUserId
     *            ユーザID
     * @param loginId
     *            ログインID
     * @param userName
     *            ユーザ名
     * @param password
     *            パスワード
     */
    public User(final UserId paramUserId, final LoginId loginId, final UserName userName, final Password password) {

        this.userId = paramUserId;
        this.loginId = loginId;
        this.userName = userName;
        this.password = password;
    }

    /**
     * 登録済みのユーザかを判定する.
     * 
     * @return 登録済みの場合：true、未登録の場合：false
     */
    public boolean isRegistered() {

        return userId.isSetValue();
    }
}