package jp.glory.todo.context.base.web.api.response;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * 入力エラーがあった場合のレスポンス.
 * @author Junki Yamada
 *
 */
@ApiModel
public class InvalidErrorResponse {

    /**
     * エラーメッセージリスト.
     */
    @Getter
    @ApiModelProperty
    private final List<String> errors = new ArrayList<>();
}
