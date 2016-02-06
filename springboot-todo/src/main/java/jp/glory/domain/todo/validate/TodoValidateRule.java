package jp.glory.domain.todo.validate;

import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.domain.common.validate.ValidateRule;
import jp.glory.domain.todo.entity.Todo;

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

        errors.addAll(todo.getSummary().validate());
        errors.addAll(todo.getMemo().validate());

        return errors;
    }

}
