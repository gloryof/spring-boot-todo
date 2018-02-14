package jp.glory.todo.context.todo.domain.specification;

import java.util.Optional;

import jp.glory.todo.context.base.domain.error.ErrorInfo;
import jp.glory.todo.context.base.domain.error.ValidateError;
import jp.glory.todo.context.base.domain.error.ValidateErrors;
import jp.glory.todo.context.base.domain.validate.ValidateRule;
import jp.glory.todo.context.todo.domain.entity.Todo;
import jp.glory.todo.context.todo.domain.repository.TodoRepository;

/**
 * TODOの更新時の仕様.
 * @author Junki Yamada
 *
 */
public class TodoUpdateSpec implements ValidateRule {

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
     * @param repository TODOリポジトリ
     * @param saveTodo 保存対象のTODO
     */
    public TodoUpdateSpec(final TodoRepository repository, final Todo saveTodo) {

        this.repository = repository;
        this.saveTodo = saveTodo;
    }

    /**
     * TODOの更新時の入力検証を行う.<br>
     * <br>
     * <dl>
     *  <dt>単項目チェック</dt>
     *  <dd>{@link #validateSingleItems()}の仕様を満たしているかを検証する。</dd>
     *  <dt>相関チェック</dt>
     *  <dd>{@link #validateRelativeItems()}の仕様をみたいしているか検証する。</dd>
     * </dl>
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
     * 下記の検証を行う.<br>
     * <br>
     * <dl>
     *  <dt>ID</dt>
     *  <dd>設定されていること</dd>
     *  <dt>上記以外の項目</dt>
     *  <dd>{@link TodoInputSpec#validate()}の仕様を満たしていること</dd>
     * </dl>
     * @return 入力チェックエラー
     */
    private ValidateErrors validateSingleItems() {

        final ValidateErrors errors = new ValidateErrors();

        if (!saveTodo.getId().isSetValue()) {

            errors.add(new ValidateError(ErrorInfo.NotRegister, Todo.LABEL));
        }

        final TodoInputSpec commonRule = new TodoInputSpec(saveTodo);
        errors.addAll(commonRule.validate());

        return errors;
    }

    /**
     * 相関チェック.<br>
     * <br>
     * <dt>
     *  <dt>ID存在チェック</dt>
     *  <dd>
     *    対象のTODOのIDが保存されているかを検証する。<br>
     *    存在しない場合はエラーとして扱う。
     *  </dd>
     *  <dt>ユーザIDチェック</dt>
     *  <dd>
     *    保存するTODOが自分自身のものかを検証する。<br>
     *    他のユーザのものだった場合はエラーとして扱う。
     *  </dd>
     * </dt>
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
