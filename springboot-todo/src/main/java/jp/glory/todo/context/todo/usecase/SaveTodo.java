package jp.glory.todo.context.todo.usecase;

import org.springframework.beans.factory.annotation.Autowired;

import jp.glory.todo.context.base.domain.error.ValidateErrors;
import jp.glory.todo.context.base.domain.validate.ValidateRule;
import jp.glory.todo.context.base.usecase.Usecase;
import jp.glory.todo.context.todo.domain.entity.Todo;
import jp.glory.todo.context.todo.domain.repository.TodoRepository;
import jp.glory.todo.context.todo.domain.specification.TodoInputSpec;
import jp.glory.todo.context.todo.domain.specification.TodoUpdateSpec;
import jp.glory.todo.context.todo.domain.value.TodoId;
import lombok.Getter;

/**
 * TODOを保存する.
 * @author Junki Yamada
 *
 */
@Usecase
public class SaveTodo {

    /**
     * TODOリポジトリ.
     */
    private final TodoRepository repository;

    /**
     * コンストラクタ.
     * @param repository リポジトリ
     */
    @Autowired
    public SaveTodo(final TodoRepository repository) {

        this.repository = repository;
    }

    /**
     * TODOを保存する.
     * @param todo 保存するTODO
     * @return 登録結果
     */
    public Result save(final Todo todo) {

        final ValidateErrors errors = validate(todo);

        if (errors.hasError()) {

            return new Result(errors);
        }

        final TodoId savedTodoId = repository.save(todo); 

        return new Result(savedTodoId);
    }

    /**
     * 入力チェックを行う.
     * @param todo TODO情報
     * @return 入力チェックエラー
     */
    private ValidateErrors validate(final Todo todo) {

        final ValidateRule rule;
        if (todo.isRegistered()) {

            rule = new TodoUpdateSpec(repository, todo);
        } else {

            rule = new TodoInputSpec(todo);
        }

        return rule.validate();
    }

    /**
     * 処理結果.
     * @author Junki Yamada
     *
     */
    public class Result {

        /**
         * 保存されたTODOのID.
         */
        @Getter
        private final TodoId savedTodoId;

        /**
         * 入力エラーリスト.
         */
        @Getter
        private final ValidateErrors errors;

        /**
         * コンストラクタ.<br>
         * 保存されたTODOのIDが設定された状態で作成する。<br>
         * 入力チェックエラーがない状態.
         * 
         * @param savedTodoId 保存されたTODOのID
         */
        private Result(final TodoId savedTodoId) {

            this.savedTodoId = savedTodoId;
            this.errors = new ValidateErrors();
        }

        /**
         * コンストラクタ.<br>
         * 入力チェックエラーが設定された状態で作成する。<br>
         * TODOのIDは発行されていない状態.
         * 
         * @param errors 入力チェックエラー
         */
        private Result(final ValidateErrors errors) {

            this.savedTodoId = TodoId.notNumberingValue();
            this.errors = errors;
        }
    }
}
