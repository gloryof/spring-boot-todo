package jp.glory.web.api.todo.response;

import jp.glory.domain.todo.entity.Todo;
import lombok.Getter;

/**
 * TODOの詳細.
 * 
 * @author Junki Yamada
 *
 */
public class TodoDetail {

    /**
     * TODOのID.
     */
    @Getter
    private final long id;

    /**
     * 概要.
     */
    @Getter
    private final String summary;

    /**
     * メモ.
     */
    @Getter
    private final String memo;

    /**
     * 完了フラグ.
     */
    @Getter
    private final boolean completed;

    /**
     * コンストラクタ.
     * 
     * @param todo
     *            TODOエンティティ
     */
    public TodoDetail(final Todo todo) {

        this.id = todo.getId().getValue();
        this.summary = todo.getSummary().getValue();
        this.memo = todo.getMemo().getValue();
        this.completed = todo.isCompleted();
    }
}
