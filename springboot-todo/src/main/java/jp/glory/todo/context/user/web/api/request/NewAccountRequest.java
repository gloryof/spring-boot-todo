package jp.glory.todo.context.user.web.api.request;

import jp.glory.todo.context.base.web.api.OriginalRequestlDoc;
import jp.glory.todo.context.user.domain.value.LoginId;
import jp.glory.todo.context.user.domain.value.Password;
import jp.glory.todo.context.user.domain.value.UserName;
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
