package jp.glory.web.api.account.request;

import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.Password;
import jp.glory.domain.user.value.UserName;
import jp.glory.framework.doc.api.annotation.OriginalRequestlDoc;
import lombok.Getter;
import lombok.Setter;

/**
 * 新アカウント作成リクエスト.
 * 
 * @author Junki Yamada
 *
 */
public class NewAccountRequest {

    @OriginalRequestlDoc(
            name = "ログインID",
            validateType = LoginId.class,
            key = true
    )
    @Getter
    @Setter
    private String loginId;

    @OriginalRequestlDoc(
            name = "ユーザ名",
            validateType = UserName.class,
            additionalRestrictions = "登録されている値と重複していないこと"
    )
    @Getter
    @Setter
    private String userName;

    @OriginalRequestlDoc(
            name = "パスワード",
            validateType = Password.class
    )
    @Getter
    @Setter
    private String password;
}
