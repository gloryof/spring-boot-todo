package jp.glory.domain.todo.validate;

import jp.glory.domain.common.error.ErrorInfo;
import jp.glory.domain.common.error.ValidateError;
import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.domain.common.validate.ValidateRule;
import jp.glory.domain.todo.entity.Todo;
import jp.glory.domain.user.entity.User;

/**
 * TODOの入力チェックルール.
 * 
 * @author Junki Yamada
 *
 */
public class TodoValidateRule implements ValidateRule {

    /**
     * チェック対象のTODO.
     */
    private final Todo todo;

    public TodoValidateRule(final Todo todo) {

        this.todo = todo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidateErrors validate() {

        final ValidateErrors errors = new ValidateErrors();

        if (!this.todo.getUserId().isSetValue()) {

            errors.add(new ValidateError(ErrorInfo.Required, User.LABEL));
        }

        errors.addAll(todo.getSummary().validate());
        errors.addAll(todo.getMemo().validate());

        return errors;
    }

}
