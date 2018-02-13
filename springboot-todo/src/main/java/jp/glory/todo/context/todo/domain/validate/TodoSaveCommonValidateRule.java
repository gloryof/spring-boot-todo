package jp.glory.todo.context.todo.domain.validate;

import jp.glory.todo.context.base.domain.error.ErrorInfo;
import jp.glory.todo.context.base.domain.error.ValidateError;
import jp.glory.todo.context.base.domain.error.ValidateErrors;
import jp.glory.todo.context.base.domain.validate.ValidateRule;
import jp.glory.todo.context.todo.domain.entity.Todo;
import jp.glory.todo.context.user.domain.entity.RegisteredUser;

/**
 * TODO保存の共通入力チェックルール.
 * 
 * @author Junki Yamada
 *
 */
public class TodoSaveCommonValidateRule implements ValidateRule {

    /**
     * チェック対象のTODO.
     */
    private final Todo todo;

    public TodoSaveCommonValidateRule(final Todo todo) {

        this.todo = todo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidateErrors validate() {

        final ValidateErrors errors = new ValidateErrors();

        if (!this.todo.getUserId().isSetValue()) {

            errors.add(new ValidateError(ErrorInfo.Required, RegisteredUser.LABEL));
        }

        errors.addAll(todo.getSummary().validate());
        errors.addAll(todo.getMemo().validate());

        return errors;
    }

}
