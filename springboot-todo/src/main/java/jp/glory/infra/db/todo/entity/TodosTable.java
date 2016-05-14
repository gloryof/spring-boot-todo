package jp.glory.infra.db.todo.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

import lombok.Getter;
import lombok.Setter;

/**
 * todosテーブル.
 * @author Junki Yamada
 *
 */
@Entity
@Table(name = "todos")
public class TodosTable {

    /**
     * TODOのID.
     */
    @Getter
    @Setter
    @Id
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
    @Version
    private long version;
}
