package jp.glory.todo.context.todo.web.api.response;

import io.swagger.annotations.ApiModelProperty;
import jp.glory.todo.context.todo.domain.entity.Todo;
import lombok.Getter;

/**
 * TODO詳細のレスポンス.
 * 
 * @author Junki Yamada
 *
 */
public class TodoDetailResponse {

    @Getter
    @ApiModelProperty(value = "ID", position = -1, required = true, readOnly = true)
    private final long id;

    @Getter
    @ApiModelProperty(value = "概要", notes = "改行は含まれない",  required = true, readOnly = true)
    private final String summary;

    @Getter
    @ApiModelProperty(value = "メモ", notes = "改行が含まれる",  readOnly = true)
    private final String memo;

    @Getter
    @ApiModelProperty(value = "完了フラグ", notes = "完了している場合：true  \r\n完了していない場合：false",  required = true, readOnly = true)
    private final boolean completed;

    @Getter
    @ApiModelProperty(value = "バージョン", notes = "更新バージョン。  \r\n更新が行われるたびにインクリメントされる",  required = true, readOnly = true)
    private final long version;

    /**
     * コンストラクタ.
     * 
     * @param todo
     *            TODOエンティティ
     */
    public TodoDetailResponse(final Todo todo) {

        this.id = todo.getId().getValue();
        this.summary = todo.getSummary().getValue();
        this.memo = todo.getMemo().getValue();
        this.completed = todo.isCompleted();
        this.version = todo.getEntityVersion();
    }
}
