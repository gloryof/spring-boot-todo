package jp.glory.web.api.todo.request;

import lombok.Getter;
import lombok.Setter;

/**
 * TODO詳細の保存リクエスト.
 * @author Junki Yamada
 *
 */
public class TodoDetailSaveRequest {

    /**
     * 概要.
     */
    @Setter
    @Getter
    private String summary;

    /**
     * メモ.
     */
    @Setter
    @Getter
    private String memo;

    /**
     * 完了フラグ.
     */
    @Setter
    @Getter
    private boolean completed;
}
