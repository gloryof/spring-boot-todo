package jp.glory.infra.db.todo.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import lombok.Getter;
import lombok.Setter;

/**
 * TODO情報.
 * @author Junki Yamada
 *
 */
@Entity
public class TodoInfo {

    /**
     * TODOのID.
     */
    @Getter
    @Setter
    @Column(name = "todo_id")
    private long todoId;

    /**
     * ユーザID.
     */
    @Getter
    @Setter
    @Column(name = "user_id")
    private long userId;

    /**
     * 概要.
     */
    @Getter
    @Setter
    @Column
    private String summary;

    /**
     * メモ.
     */
    @Getter
    @Setter
    @Column
    private String memo;

    /**
     * 完了フラグ.
     */
    @Getter
    @Setter
    @Column
    private boolean completed;
}
