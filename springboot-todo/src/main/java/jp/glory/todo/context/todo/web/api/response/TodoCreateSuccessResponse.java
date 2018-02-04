package jp.glory.todo.context.todo.web.api.response;

import io.swagger.annotations.ApiModelProperty;
import jp.glory.todo.context.todo.domain.value.TodoId;
import lombok.Getter;

/**
 * TODO作成の保存成功レスポンス.
 * @author Junki Yamada
 *
 */
public class TodoCreateSuccessResponse {

    @Getter
    @ApiModelProperty(
            value = "作成されたTODOのID",
            required = true,
            readOnly = true)
    private final long id;

    /**
     * コンストラクタ.
     * @param id TODOのID.
     */
    public TodoCreateSuccessResponse(final TodoId id) {

        this.id = id.getValue();
    }
}
