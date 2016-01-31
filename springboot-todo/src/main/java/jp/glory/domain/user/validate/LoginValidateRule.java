package jp.glory.domain.user.validate;

import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.domain.common.validate.ValidateRule;
import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.Password;

/**
 * ログイン時の検証ルール.
 *
 * @author Junki Yamada
 */
public class LoginValidateRule implements ValidateRule {

    /**
     * ログインID.
     */
    private final LoginId loginId;

    /**
     * パスワード.
     */
    private final Password password;

    /**
     * コンストラクタ.
     *
     * @param loginId
     *            ログインID
     * @param password
     *            パスワード
     */
    public LoginValidateRule(final LoginId loginId, final Password password) {

        this.loginId = loginId;
        this.password = password;
    }

    /**
     * 入力チェックを行う.
     *
     * @return 入力チェック結果
     */
    @Override
    public ValidateErrors validate() {

        final ValidateErrors errors = new ValidateErrors();

        errors.addAll(loginId.validate());
        errors.addAll(password.validate());

        return errors;
    }
}