package jp.glory.web.api.account.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * 新アカウント作成レスポンス.
 * 
 * @author Junki Yamada
 *
 */
public class NewAccountResponse {

    /**
     * エラーメッセージリスト.
     */
    @Getter
    private final List<String> errors = new ArrayList<>();
}
