package jp.glory.todo.context.base.domain.type;

import jp.glory.todo.context.base.domain.error.ValidateErrors;

/**
 * 入力チェック可能インターフェイス
 * @author Junki Yamada
 */
public interface Validatable {

    /**
     * 入力情報の検証を行う.
     * @return 検証結果
     */
    default ValidateErrors validate() {
        
        return new ValidateErrors();
    }
}