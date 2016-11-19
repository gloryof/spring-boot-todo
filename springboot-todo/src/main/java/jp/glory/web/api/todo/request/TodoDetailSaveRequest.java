package jp.glory.web.api.todo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO詳細の保存リクエスト.
 * @author Junki Yamada
 *
 */
public class TodoDetailSaveRequest {

    @Setter
    @Getter
    @ApiModelProperty(
            value = "[項目名]  \r\n概要\r\n\r\n[制限]\r\n- 必須\r\n- 20文字以下",
            required = true)
    private String summary;

    @Setter
    @Getter
    @ApiModelProperty(
            value = "[項目名]  \r\nメモ\r\n\r\n[制限]  \r\n- 1000文字以下",
            required = false)
    private String memo;

    @Setter
    @Getter
    @ApiModelProperty(
            value = "[項目名]  \r\n完了フラグ",
            required = true)
    private boolean completed;

    /**
     * バージョン.
     */
    @Setter
    @Getter
    @ApiModelProperty(
            value = "[項目名]  \r\nバージョン",
            required = true)
    private long version;
}
