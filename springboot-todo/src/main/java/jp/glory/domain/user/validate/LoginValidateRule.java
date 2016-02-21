package jp.glory.domain.user.validate;

import java.util.Optional;

import jp.glory.domain.common.error.ErrorInfo;
import jp.glory.domain.common.error.ValidateError;
import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.domain.common.validate.ValidateRule;
import jp.glory.domain.user.entity.User;
import jp.glory.domain.user.repository.UserRepository;
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
     * リポジトリ.
     */
    private final UserRepository repository;

    /**
     * コンストラクタ.
     *
     * @param loginId
     *            ログインID
     * @param password
     *            パスワード
     * @param repository
     *            リポジトリ
     */
    public LoginValidateRule(final LoginId loginId, final Password password, final UserRepository repository) {

        this.loginId = loginId;
        this.password = password;
        this.repository = repository;
    }

    /**
     * 入力チェックを行う.
     *
     * @return 入力チェック結果
     */
    @Override
    public ValidateErrors validate() {

        final ValidateErrors errors = new ValidateErrors();
        errors.addAll(validateSingleItems());

        if (errors.hasError()) {

            return errors;
        }

        if (!isAuthenticateSuccess(loginId, password)) {

            errors.add(new ValidateError(ErrorInfo.LoginFailed));
        }

        return errors;
    }

    /**
     * 各項目ごとの入力チェックを行う.
     * 
     * @return 入力チェックエラー
     */
    private ValidateErrors validateSingleItems() {

        final ValidateErrors errors = new ValidateErrors();

        errors.addAll(loginId.validate());
        errors.addAll(password.validate());

        return errors;
    }

    /**
     * 認証に成功するかチェックする.
     * 
     * @param loginId
     *            ログインID
     * @param password
     *            パスワード
     * @return 認証に成功した場合：true、認証に成功していない場合：false
     */
    private boolean isAuthenticateSuccess(final LoginId loginId, final Password password) {

        final Optional<User> optUser = repository.findBy(loginId);

        return optUser.filter(v -> v.getPassword().isMatch(password)).isPresent();
    }
}