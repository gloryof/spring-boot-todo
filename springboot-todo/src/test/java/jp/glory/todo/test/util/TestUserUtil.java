package jp.glory.todo.test.util;

import jp.glory.todo.context.user.domain.entity.RegisteredUser;
import jp.glory.todo.context.user.domain.value.LoginId;
import jp.glory.todo.context.user.domain.value.Password;
import jp.glory.todo.context.user.domain.value.UserId;
import jp.glory.todo.context.user.domain.value.UserName;

public class TestUserUtil {

    public static RegisteredUser createDefault() {

        final UserId paramUserId = new UserId(100l);
        final LoginId loginId = new LoginId("test-login");
        final Password password = new Password("password");
        final UserName userName = new UserName("テストユーザ");

        return new RegisteredUser(paramUserId, loginId, userName, password);
    }
}
