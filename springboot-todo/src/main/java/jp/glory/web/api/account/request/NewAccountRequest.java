package jp.glory.web.api.account.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 新アカウント作成リクエスト.
 * 
 * @author Junki Yamada
 *
 */
public class NewAccountRequest {

    /** ログインID. */
    @Getter
    @Setter
    private String loginId;

    /** ユーザ名. */
    @Getter
    @Setter
    private String userName;

    /** パスワード. */
    @Getter
    @Setter
    private String password;
}
