package jp.glory.web.api.todo.response;

import java.util.List;
import java.util.stream.Collectors;

import jp.glory.domain.todo.entity.Todos;
import lombok.Getter;

/**
 * TODO一覧のレスポンス.
 * 
 * @author Junki Yamada
 *
 */
public class TodoListResponse {

    /**
     * TODOのリスト.
     */
    @Getter
    private final List<TodoDetailResponse> details;

    /**
     * 統計.
     */
    @Getter
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
