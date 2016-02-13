package jp.glory.usecase.user;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import jp.glory.domain.user.entity.User;
import jp.glory.domain.user.repository.UserRepositoryMock;
import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.Password;
import jp.glory.domain.user.value.UserName;

@RunWith(Enclosed.class)
public class CreateNewAccountTest {

    @RunWith(Enclosed.class)
    public static class create {

        public static class 入力内容に不備がない場合 {

            private CreateNewAccount sut = null;
            private UserRepositoryMock mock = null;
            private LoginId inputLoginId = null;
            private CreateNewAccount.Result actual = null;

            @Before
            public void setUp() {

                mock = new UserRepositoryMock();
                sut = new CreateNewAccount(mock);

                inputLoginId = new LoginId("test-user");
                actual = sut.create(inputLoginId, new UserName("ユーザー"), new Password("password"));
            }

            @Test
            public void 入力エラーはない() {

                assertThat(actual.getErrors().hasError(), is(false));
            }

            @Test
            public void 対象のユーザが登録されている() {

                final Optional<User> actualOpt = mock.findBy(inputLoginId);
                assertThat(actualOpt.isPresent(), is(true));
            }
        }
    }

    public static class 入力内容に不備がある場合 {

        private CreateNewAccount sut = null;
        private UserRepositoryMock mock = null;
        private LoginId inputLoginId = null;
        private CreateNewAccount.Result actual = null;

        @Before
        public void setUp() {

            mock = new UserRepositoryMock();
            sut = new CreateNewAccount(mock);

            inputLoginId = new LoginId("test-user");
            actual = sut.create(inputLoginId, new UserName(""), new Password("password"));
        }

        @Test
        public void 入力エラーがある() {

            assertThat(actual.getErrors().hasError(), is(true));
        }

        @Test
        public void 対象のユーザが登録されていない() {

            final Optional<User> actualOpt = mock.findBy(inputLoginId);
            assertThat(actualOpt.isPresent(), is(false));
        }
    }
}
