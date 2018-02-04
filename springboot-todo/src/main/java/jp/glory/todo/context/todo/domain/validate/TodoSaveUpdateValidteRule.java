package jp.glory.todo.context.todo.domain.validate;

import java.util.Optional;

import jp.glory.todo.context.base.domain.error.ErrorInfo;
import jp.glory.todo.context.base.domain.error.ValidateError;
import jp.glory.todo.context.base.domain.error.ValidateErrors;
import jp.glory.todo.context.base.domain.validate.ValidateRule;
import jp.glory.todo.context.todo.domain.entity.Todo;
import jp.glory.todo.context.todo.domain.repository.TodoRepository;

/**
 * TODOの更新の場合の入力チェックルール.
 * @author Junki Yamada
 *
 */
public class TodoSaveUpdateValidteRule implements ValidateRule {

    /**
     * TODOリポジトリ.
     */
    private final TodoRepository repository;

    /**
     * 保存対象のTODO.
     */
    private final Todo saveTodo;

    /**
     * コンストラクタ.
     * 
     * @param respositroy TODOリポジトリ
     * @param saveTodo 保存対象のTODO
     */
    public TodoSaveUpdateValidteRule(final TodoRepository repository, final Todo saveTodo) {

        this.repository = repository;
        this.saveTodo = saveTodo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidateErrors validate() {

        final ValidateErrors errors = validateSingleItems();

        if (!errors.hasError()) {

            errors.addAll(validateRelativeItems());
        }

        return errors;
    }

    /**
     * 単項目チェック.
     * @return 入力チェックエラー
     */
    private ValidateErrors validateSingleItems() {

        final ValidateErrors errors = new ValidateErrors();

        if (!saveTodo.getId().isSetValue()) {

            errors.add(new ValidateError(ErrorInfo.NotRegister, Todo.LABEL));
        }

        final TodoSaveCommonValidateRule commonRule = new TodoSaveCommonValidateRule(saveTodo);
        errors.addAll(commonRule.validate());

        return errors;
    }

    /**
     * 相関チェック.
     * @return 入力チェックエラー
     */
    private ValidateErrors validateRelativeItems() {

        final Optional<Todo> optTodo = repository.findBy(saveTodo.getId());
        final ValidateErrors errors = new ValidateErrors();

        if (!optTodo.isPresent()) {

            errors.add(new ValidateError(ErrorInfo.NotRegister, Todo.LABEL));
            return errors;
        }

        final Todo beforeTodo = optTodo.get();
        if (!beforeTodo.getUserId().isSame(saveTodo.getUserId())) {

            errors.add(new ValidateError(ErrorInfo.SavedToOtherUser));
        }

        return errors;
    }
}
