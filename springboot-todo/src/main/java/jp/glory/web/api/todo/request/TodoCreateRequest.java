package jp.glory.web.api.todo.request;

import jp.glory.domain.todo.value.Memo;
import jp.glory.domain.todo.value.Summary;
import jp.glory.framework.doc.api.annotation.OriginalRequestlDoc;
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
