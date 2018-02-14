package jp.glory.todo.context.todo.domain.specification;

import jp.glory.todo.context.base.domain.error.ErrorInfo;
import jp.glory.todo.context.base.domain.error.ValidateError;
import jp.glory.todo.context.base.domain.error.ValidateErrors;
import jp.glory.todo.context.base.domain.validate.ValidateRule;
import jp.glory.todo.context.todo.domain.entity.Todo;
import jp.glory.todo.context.todo.domain.value.Memo;
import jp.glory.todo.context.todo.domain.value.Summary;
import jp.glory.todo.context.user.domain.entity.RegisteredUser;

/**
 * TODOの入力仕様.
 * 
 * @author Junki Yamada
 *
 */
public class TodoInputSpec implements ValidateRule {

    /**
     * チェック対象のTODO.
     */
    private final Todo todo;

    public TodoInputSpec(final Todo todo) {

        this.todo = todo;
    }

    /**
     * TODOの入力内容の検証を行う.<br>
     * <br>
     * 検証内容は下記。<br>
     * <dl>
     *  <dt>ユーザID</dt>
     *  <dd>設定されていること</dd>
     *  <dt>概要</dt>
     *  <dd>{@link Summary}の入力仕様を満たしていること</dd>
     *  <dt>メモ</dt>
     *  <dd>{@link Memo}の入力仕様を満たしていること</dd>
     * </dl>
     */
    @Override
    public ValidateErrors validate() {

        final ValidateErrors errors = new ValidateErrors();

        if (!this.todo.getUserId().isSetValue()) {

            errors.add(new ValidateError(ErrorInfo.Required, RegisteredUser.LABEL));
        }

        errors.addAll(todo.getSummary().validate());
        todo.getMemo()
            .map(Memo::validate)
            .ifPresent(errors::addAll);

        return errors;
    }

}
