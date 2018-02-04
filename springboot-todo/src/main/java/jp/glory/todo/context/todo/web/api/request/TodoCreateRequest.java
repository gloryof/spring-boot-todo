package jp.glory.todo.context.todo.web.api.request;

import jp.glory.todo.context.base.web.api.OriginalRequestlDoc;
import jp.glory.todo.context.todo.domain.value.Memo;
import jp.glory.todo.context.todo.domain.value.Summary;
import lombok.Getter;
import lombok.Setter;

/**
 * TODOの作成リクエスト.
 * @author Junki Yamada
 *
 */
public class TodoCreateRequest {

    @OriginalRequestlDoc(
            name = "概要",
            validateType = Summary.class
    )
    @Setter
    @Getter
    private String summary;

    @OriginalRequestlDoc(
            name = "メモ",
            validateType = Memo.class
    )
    @Setter
    @Getter
    private String memo;


    @OriginalRequestlDoc(
            name = "完了フラグ",
            requied = true
    )
    @Setter
    @Getter
    private boolean completed;
}
