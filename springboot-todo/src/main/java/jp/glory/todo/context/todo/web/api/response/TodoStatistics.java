package jp.glory.todo.context.todo.web.api.response;

import io.swagger.annotations.ApiModelProperty;
import jp.glory.todo.context.todo.domain.entity.Todos;
import lombok.Getter;

/**
 * TODOの統計.
 * 
 * @author Junki Yamada
 *
 */
public class TodoStatistics {

    @Getter
    @ApiModelProperty(value = "TODOの合計件数", notes = "実行済み件数 + 未実行件数となる。",  required = true, readOnly = true)
    private final int total;

    @Getter
    @ApiModelProperty(value = "実行済み件数", notes = "実行済みのTODOの件数。",  required = true, readOnly = true)
    private final int executed;

    @Getter
    @ApiModelProperty(value = "未実行件数", notes = "未実行件数のTODOの件数。",  required = true, readOnly = true)
    private final int unexecuted;

    /**
     * コンストラクタ.
     * 
     * @param todos
     *            TODOリスト
     */
    public TodoStatistics(final Todos todos) {

        this.total = todos.getStatistics().getTotal();
        this.executed = todos.getStatistics().getExecuted();
        this.unexecuted = todos.getStatistics().getUnexecuted();
    }
}
