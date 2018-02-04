package jp.glory.todo.context.base.domain.validate;

import jp.glory.todo.context.base.domain.error.ValidateErrors;

/**
 * 入力チェックインターフェイス.
 *
 * @author Junki Yamada
 */
public interface ValidateRule {

    /**
     * 入力チェックを行う.<br>
     * 入力チェックエラーがない場合は空のValidateErrorsを返却すること
     *
     * @return 入力チェック結果
     */
    ValidateErrors validate();
}