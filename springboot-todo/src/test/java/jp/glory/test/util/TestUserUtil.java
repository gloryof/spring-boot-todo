package jp.glory.test.util;

import jp.glory.domain.user.entity.User;
import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.Password;
import jp.glory.domain.user.value.UserId;
import jp.glory.domain.user.value.UserName;

public class TestUserUtil {

    public static User createDefault() {

        final UserId paramUserId = new UserId(100l);
        final LoginId loginId = new LoginId("test-login");
        final Password password = new Password("password");
        final UserName userName = new UserName("テストユーザ");

        return new User(paramUserId, loginId, userName, password);
    }
}
