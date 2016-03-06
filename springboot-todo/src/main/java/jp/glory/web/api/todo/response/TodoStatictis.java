package jp.glory.web.api.todo.response;

import jp.glory.domain.todo.entity.Todos;
import lombok.Getter;

/**
 * TODOの統計.
 * 
 * @author Junki Yamada
 *
 */
public class TodoStatictis {

    /**
     * 合計件数.
     */
    @Getter
    private final int total;

    /**
     * 実行済件数.
     */
    @Getter
    private final int executed;

    /**
     * 未実行件数.
     */
    @Getter
    private final int unexecuted;

    /**
     * コンストラクタ.
     * 
     * @param todos
     *            TODOリスト
     */
    public TodoStatictis(final Todos todos) {

        this.total = todos.getStatistics().getTotal();
        this.executed = todos.getStatistics().getExecuted();
        this.unexecuted = todos.getStatistics().getUnexecuted();
    }
}
