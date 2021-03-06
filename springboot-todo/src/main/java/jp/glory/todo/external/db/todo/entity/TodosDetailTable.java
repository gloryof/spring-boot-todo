package jp.glory.todo.external.db.todo.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * todo_detailsテーブル.
 * @author Junki Yamada
 *
 */
@Entity
@Table(name = "todos_detail")
public class TodosDetailTable {

    /**
     * TODOのID.
     */
    @Getter
    @Setter
    @Id
    private long todoId;

    /**
     * メモ.
     */
    @Getter
    @Setter
    private String memo;
}
