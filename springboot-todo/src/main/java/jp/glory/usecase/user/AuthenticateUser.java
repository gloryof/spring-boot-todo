package jp.glory.usecase.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.domain.user.repository.UserRepository;
import jp.glory.domain.user.validate.LoginValidateRule;
import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.Password;
import lombok.Getter;

/**
 * ユーザを認証する.
 * 
 * @author Junki Yamada
 *
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class AuthenticateUser {

    /**
     * ユーザリポジトリ.
     */
    private final UserRepository repository;

    /**
     * コンストラクタ.
     * 
     * @param repository
     *            ユーザリポジトリ
     */
    @Autowired
    public AuthenticateUser(final UserRepository repository) {

        this.repository = repository;
    }

    /**
     * 認証処理.
     * 
     * @param loginId
     *            ログインID
     * @param password
     *            パスワード
     * @return 認証結果
     */
    public Result authenticate(final LoginId loginId, final Password password) {

        final LoginValidateRule rule = new LoginValidateRule(loginId, password, repository);
        final Result result = new Result(rule.validate());

        return result;
    }

    /**
     * 処理結果.
     * 
     * @author Junki Yamada
     *
     */
    public static class Result {

        /**
         * 入力チェック結果.
         */
        @Getter
        private final ValidateErrors errors;

        /**
         * コンストラクタ.
         * 
         * @param errors
         *            入力チェック結果
         */
        protected Result(final ValidateErrors errors) {

            this.errors = errors;
        }
    }
}
