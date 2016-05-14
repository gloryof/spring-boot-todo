package jp.glory.infra.db.todo.entity;

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
    private long todoId;

    /**
     * ユーザID.
     */
    @Getter
    @Setter
    private long userId;

    /**
     * 概要.
     */
    @Getter
    @Setter
    private String summary;

    /**
     * メモ.
     */
    @Getter
    @Setter
    private String memo;

    /**
     * 完了フラグ.
     */
    @Getter
    @Setter
    private boolean completed;

    /**
     * バージョン.
     */
    @Getter
    @Setter
    private long version;
}
