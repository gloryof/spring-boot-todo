package jp.glory.web.api.todo.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * TOD作成で入力エラーがあった場合のレスポンス.
 * @author Junki Yamada
 *
 */
public class TodoCreateErrorResponse {

    /**
     * エラーメッセージリスト.
     */
    @Getter
    private final List<String> errors = new ArrayList<>();
}
