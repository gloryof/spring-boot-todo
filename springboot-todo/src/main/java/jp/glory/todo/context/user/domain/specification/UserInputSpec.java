package jp.glory.todo.context.user.domain.specification;

import java.util.Optional;

import jp.glory.todo.context.base.domain.error.ErrorInfo;
import jp.glory.todo.context.base.domain.error.ValidateError;
import jp.glory.todo.context.base.domain.error.ValidateErrors;
import jp.glory.todo.context.base.domain.validate.ValidateRule;
import jp.glory.todo.context.user.domain.entity.RegisteredUser;
import jp.glory.todo.context.user.domain.repository.RegisteredUserRepository;
import jp.glory.todo.context.user.domain.value.LoginId;
import jp.glory.todo.context.user.domain.value.Password;
import jp.glory.todo.context.user.domain.value.UserId;
import jp.glory.todo.context.user.domain.value.UserName;

/**
 * ユーザの入力値に関する仕様.
 *
 * @author Junki Yamada
 */
public class UserInputSpec implements ValidateRule {

    /** チェック対象ユーザ. */
    private final RegisteredUser user;

    /**
     * ユーザリポジトリ.
     */
    private final RegisteredUserRepository repository;

    /**
     * コンストラクタ.
     * 
     * @param user
     *            ユーザ
     * @param repository
     *            リポジトリ
     */
    public UserInputSpec(final RegisteredUser user, final RegisteredUserRepository repository) {

        this.user = user;
        this.repository = repository;
    }

    /**
     * 入力情報の検証を行う.<br>
     * 入力仕様は下記。<br>
     * <dl>
     *  <dt>ログインID</dt>
     *  <dd>{@link LoginId}の入力仕様を満たしていること</dd>
     *  <dt>ユーザ名</dt>
     *  <dd>{@link UserName}の入力仕様を満たしていること</dd>
     *  <dt>パスワード</dt>
     *  <dd>{@link Password}の入力仕様を満たしていること</dd>
     *  <dt>重複チェック</dt>
     *  <dd>{@link #isDuplicated()}のチェックを行い重複していないこと</dd>
     * </dl>
     * 
     * @return 検証結果
     */
    @Override
    public ValidateErrors validate() {

        final ValidateErrors errors = new ValidateErrors();

        errors.addAll(user.getLoginId().validate());
        errors.addAll(user.getUserName().validate());
        errors.addAll(user.getPassword().validate());

        if (isDuplicated()) {

            errors.add(new ValidateError(ErrorInfo.LoginIdDuplicated, LoginId.LABEL));
        }

        return errors;
    }

    /**
     * 重複しているかを判定する.<br>
     * 異なるユーザIDでログインIDが同じ場合は重複として扱う。
     *
     * @return 重複している場合：true、重複していない場合：false
     */
    private boolean isDuplicated() {

        final Optional<RegisteredUser> optUser = repository.findBy(user.getLoginId());

        if (!optUser.isPresent()) {

            return false;
        }

        final RegisteredUser savedUser = optUser.get();
        final UserId savedUserId = savedUser.getUserId();

        return !(savedUserId.isSame(user.getUserId()));
    }
}