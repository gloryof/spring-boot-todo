package jp.glory.domain.todo.entity;

import jp.glory.domain.todo.value.Memo;
import jp.glory.domain.todo.value.Summary;
import jp.glory.domain.todo.value.TodoId;
import lombok.Getter;

/**
 * TODOの内容.
 * 
 * @author Junki Yamada
 *
 */
public class Todo {

    /**
     * ID.
     */
    @Getter
    private final TodoId id;

    /**
     * 概要.
     */
    @Getter
    private final Summary summary;

    /**
     * メモ.
     */
    @Getter
    private final Memo memo;

    /**
     * 完了フラグ.<br>
     * 完了している場合：true、完了していない場合：false
     */
    @Getter
    private boolean completed = false;

    /**
     * コンストラクタ.
     * 
     * @param id
     *            TODOのID
     * @param summary
     *            概要
     * @param memo
     *            メモ
     * @param completed
     *            完了フラグ
     */
    public Todo(final TodoId id, final Summary summary, final Memo memo, final boolean completed) {

        this.id = id;
        this.summary = summary;
        this.memo = memo;
        this.completed = completed;
    }

    /**
     * 登録済みのTODOかを判定する.
     * 
     * @return 登録済みの場合：true、未登録の場合：false
     */
    public boolean isRegistered() {

        return id.isSetValue();
    }

    /**
     * 完了済みとしてマークする.
     */
    public void markAsComplete() {

        this.completed = true;
    }

    /**
     * 完了済みのマークを解除する.
     */
    public void unmarkFromComplete() {

        this.completed = false;
    }
}
