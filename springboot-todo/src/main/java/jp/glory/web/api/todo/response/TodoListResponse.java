package jp.glory.web.api.todo.response;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiModelProperty;
import jp.glory.domain.todo.entity.Todos;
import lombok.Getter;

/**
 * TODO一覧のレスポンス.
 * 
 * @author Junki Yamada
 *
 */
public class TodoListResponse {

    @Getter
    @ApiModelProperty(value = "TODOリスト", required = true, readOnly = true)
    private final List<TodoDetailResponse> details;

    @Getter
    @ApiModelProperty(value = "TODOの統計情報", notes = "件数に関する情報を保持する。",  required = true, readOnly = true)
    private final TodoStatistics statistics;

    /**
     * コンストラクタ.
     * 
     * @param todos
     *            TODOリスト
     */
    public TodoListResponse(final Todos todos) {

        details = todos.asList().stream().map(TodoDetailResponse::new).collect(Collectors.toList());
        statistics = new TodoStatistics(todos);
    }
}
