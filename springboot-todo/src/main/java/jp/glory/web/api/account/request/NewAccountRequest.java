package jp.glory.web.api.account.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

/**
 * 新アカウント作成リクエスト.
 * 
 * @author Junki Yamada
 *
 */
public class NewAccountRequest {

    @ApiModelProperty(
            value = "[項目名]  \r\nログインID\r\n\r\n[制限]\r\n- 必須\r\n- 50文字以下\r\n- 半角文字のみ",
            required = true)
    @Getter
    @Setter
    private String loginId;

    @ApiParam(
            value = "[項目名]  \r\nユーザ名\r\n\r\n[制限]\r\n- 必須\r\n- 50文字以下\r\n- 登録されている値と重複していないこと",
            required = true)
    @Getter
    @Setter
    private String userName;

    /** パスワード. */
    @Getter
    @Setter
    @ApiParam(
            value = "[項目名]  \r\nパスワード\r\n\r\n[制限]\r\n- 必須\r\n- 半角文字のみ",
            required = true)
    private String password;
}
