package jp.glory.todo.context.user.usecase;

import org.springframework.beans.factory.annotation.Autowired;

import jp.glory.todo.context.base.domain.error.ValidateErrors;
import jp.glory.todo.context.base.usecase.Usecase;
import jp.glory.todo.context.user.domain.entity.RegisteredUser;
import jp.glory.todo.context.user.domain.repository.RegisteredUserRepository;
import jp.glory.todo.context.user.domain.specification.UserInputSpec;
import jp.glory.todo.context.user.domain.value.LoginId;
import jp.glory.todo.context.user.domain.value.Password;
import jp.glory.todo.context.user.domain.value.UserId;
import jp.glory.todo.context.user.domain.value.UserName;
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
    private final RegisteredUserRepository repository;

    /**
     * コンストラクタ.
     * 
     * @param repository
     *            ユーザリポジトリ
     */
    @Autowired
    public CreateNewAccount(final RegisteredUserRepository repository) {

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

        final RegisteredUser user = new RegisteredUser(UserId.notNumberingValue(), loginId, userName, password);
        final UserInputSpec rule = new UserInputSpec(user, repository);

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
    public class Result {

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
