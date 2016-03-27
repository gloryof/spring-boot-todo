package jp.glory.framework.web.exception.handler.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * 入力エラーがあった場合のレスポンス.
 * @author Junki Yamada
 *
 */
public class InvalidErrorResponse {

    /**
     * エラーメッセージリスト.
     */
    @Getter
    private final List<String> errors = new ArrayList<>();
}
