package jp.glory.usecase.user;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import jp.glory.domain.user.entity.User;
import jp.glory.domain.user.repository.UserRepositoryMock;
import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.Password;
import jp.glory.domain.user.value.UserId;
import jp.glory.domain.user.value.UserName;
import jp.glory.usecase.user.AuthenticateUser.Result;

@RunWith(Enclosed.class)
public class AuthenticateUserTest {

    @RunWith(Enclosed.class)
    public static class authencticate {

        public static class 入力した認証情報に合致するユーザがいる場合 {

            private AuthenticateUser sut = null;
            private UserRepositoryMock mock = null;
            private Result actual = null;

            @Before
            public void setUp() {

                mock = new UserRepositoryMock();
                sut = new AuthenticateUser(mock);

                final LoginId inputLoginId = new LoginId("test-user");
                final Password inputPassword = new Password("password");
                mock.save(new User(new UserId(1l), inputLoginId, new UserName("テスト"), inputPassword));

                actual = sut.authenticate(inputLoginId, inputPassword);
            }

            @Test
            public void 入力エラーはない() {

                assertThat(actual.getErrors().hasError(), is(false));
            }
        }

        public static class 入力値に不備がある場合 {

            private AuthenticateUser sut = null;
            private UserRepositoryMock mock = null;
            private Result actual = null;

            @Before
            public void setUp() {

                mock = new UserRepositoryMock();
                sut = new AuthenticateUser(mock);

                final LoginId inputLoginId = new LoginId("test-user");
                final Password inputPassword = new Password("password");
                mock.save(new User(new UserId(1l), inputLoginId, new UserName("テスト"), inputPassword));

                actual = sut.authenticate(inputLoginId, Password.empty());
            }

            @Test
            public void 入力チェックエラーとなる() {

                assertThat(actual.getErrors().hasError(), is(true));
            }
        }

    }
}
