package jp.glory.usecase.user;

import org.springframework.beans.factory.annotation.Autowired;

import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.domain.user.entity.User;
import jp.glory.domain.user.repository.UserRepository;
import jp.glory.domain.user.validate.UserModifyCommonValidateRule;
import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.Password;
import jp.glory.domain.user.value.UserId;
import jp.glory.domain.user.value.UserName;
import jp.glory.framework.layer.usecase.annotation.Usecase;
import lombok.Getter;

/**
 * アカウント作成.
 * 
 * @author Junki Yamada
 *
 */
@Usecase
public class CreateNewAccount {

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
    public CreateNewAccount(final UserRepository repository) {

        this.repository = repository;
    }

    /**
     * 新しいユーザを作成する.
     * 
     * @param loginId
     *            ログインID
     * @param userName
     *            ユーザ名
     * @param password
     *            パスワード
     * @return 作成結果
     */
    public Result create(final LoginId loginId, final UserName userName, final Password password) {

        final User user = new User(UserId.notNumberingValue(), loginId, userName, password);
        final UserModifyCommonValidateRule rule = new UserModifyCommonValidateRule(user, repository);

        final ValidateErrors errors = rule.validate();

        if (!errors.hasError()) {

            repository.save(user);
        }

        return new Result(errors);
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
