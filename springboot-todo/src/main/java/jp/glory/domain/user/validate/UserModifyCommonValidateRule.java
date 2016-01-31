package jp.glory.domain.user.validate;

import java.util.Optional;

import jp.glory.domain.common.error.ErrorInfo;
import jp.glory.domain.common.error.ValidateError;
import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.domain.common.validate.ValidateRule;
import jp.glory.domain.user.entity.User;
import jp.glory.domain.user.repository.UserRepository;
import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.UserId;

/**
 * ユーザの編集に関する共通ルール.
 *
 * @author Junki Yamada
 */
public class UserModifyCommonValidateRule implements ValidateRule {

    /** チェック対象ユーザ. */
    private final User user;

    /**
     * ユーザリポジトリ.
     */
    private final UserRepository repository;

    /**
     * コンストラクタ.
     * 
     * @param user
     *            ユーザ
     * @param repository
     *            リポジトリ
     */
    public UserModifyCommonValidateRule(final User user, final UserRepository repository) {

        this.user = user;
        this.repository = repository;
    }

    /**
     * 入力情報の検証を行う.
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
     * 重複しているかを判定する.
     *
     * @return 重複している場合：true、重複していない場合：false
     */
    private boolean isDuplicated() {

        final Optional<User> optUser = repository.findBy(user.getLoginId());

        if (!optUser.isPresent()) {

            return false;
        }

        final User savedUser = optUser.get();
        final UserId savedUserId = savedUser.getUserId();

        return !(savedUserId.isSame(user.getUserId()));
    }
}