package jp.glory.domain.user.validate;

import jp.glory.domain.common.error.ErrorInfo;
import jp.glory.domain.common.error.ValidateError;
import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.domain.common.validate.ValidateRule;
import jp.glory.domain.user.entity.User;
import jp.glory.domain.user.repository.UserRepository;

/**
 * ユーザ情報の変更に関する入力ルール.
 *
 * @author Junki Yamada
 */
public class UserEditValidateRule implements ValidateRule {

    /**
     * 共通ルール.
     */
    private final UserModifyCommonValidateRule commonRule;

    /** チェック対象ユーザ. */
    private final User user;

    /**
     * コンストラクタ.
     * 
     * @param user
     *            ユーザ
     * @param repository
     *            リポジトリ
     */
    public UserEditValidateRule(final User user, final UserRepository repository) {

        this.user = user;
        commonRule = new UserModifyCommonValidateRule(user, repository);
    }

    /**
     * 編集時の入力チェックを行う.
     * 
     * @return エラー情報
     */
    @Override
    public ValidateErrors validate() {

        final ValidateErrors errors = commonRule.validate();

        if (!user.isRegistered()) {

            errors.add(new ValidateError(ErrorInfo.NotRegister, User.LABEL));
        }

        return errors;
    }

}