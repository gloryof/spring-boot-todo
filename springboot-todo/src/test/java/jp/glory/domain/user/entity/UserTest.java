package jp.glory.domain.user.entity;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.Password;
import jp.glory.domain.user.value.UserId;
import jp.glory.domain.user.value.UserName;

@RunWith(Enclosed.class)
public class UserTest {

    public static class 全ての値が設定されている場合 {

        private User sut;

        private final UserId USER_ID_VALUE = new UserId(123456L);

        @Before
        public void setUp() {

            sut = new User(USER_ID_VALUE, new LoginId("test-user"), new UserName("シュンツ"),
                    new Password("19CB2A070DDBE8157E17C5DDA0EA38E8AA16FAE1725C1F7AC22747D870368579"));
        }

        @Test
        public void 設定したユーザIDが設定されている() {

            Assert.assertThat(sut.getUserId().isSame(USER_ID_VALUE), CoreMatchers.is(true));
        }

        @Test
        public void isRegisteredにtrueが設定されている() {

            Assert.assertThat(sut.isRegistered(), CoreMatchers.is(true));
        }
    }

    public static class 未登録のユーザの場合 {

        private User sut;

        @Before
        public void setUp() {

            sut = new User(UserId.notNumberingValue(), new LoginId("test-user"), new UserName("シュンツ"),
                    new Password("19CB2A070DDBE8157E17C5DDA0EA38E8AA16FAE1725C1F7AC22747D870368579"));
        }

        @Test
        public void isRegisteredにfalseが設定されている() {

            Assert.assertThat(sut.isRegistered(), CoreMatchers.is(false));
        }
    }
}