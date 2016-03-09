package jp.glory.usecase.todo;

import org.springframework.beans.factory.annotation.Autowired;

import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.domain.todo.entity.Todo;
import jp.glory.domain.todo.repository.TodoRepository;
import jp.glory.domain.todo.validate.TodoValidateRule;
import jp.glory.domain.todo.value.TodoId;
import lombok.Getter;

/**
 * TODOを保存する.
 * @author Junki Yamada
 *
 */
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

        final TodoValidateRule rule = new TodoValidateRule(todo);
        final ValidateErrors errors = rule.validate();

        if (errors.hasError()) {

            return new Result(errors);
        }

        final TodoId savedTodoId = repository.save(todo); 

        return new Result(savedTodoId);
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
