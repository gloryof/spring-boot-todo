package jp.glory.usecase.user;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.glory.domain.user.entity.User;
import jp.glory.domain.user.repository.UserRepositoryMock;
import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.Password;
import jp.glory.domain.user.value.UserName;

class CreateNewAccountTest {

    private CreateNewAccount sut = null;
    private UserRepositoryMock mock = null;
    private LoginId inputLoginId = null;
    private CreateNewAccount.Result actual = null;

    @BeforeEach
    void setUp() {

        mock = new UserRepositoryMock();
        sut = new CreateNewAccount(mock);

        inputLoginId = new LoginId("test-user");
    }

    @DisplayName("createのテスト")
    @Nested
    class Create {

        @DisplayName("入力内容に不備がない場合")
        @Nested
        class WhenValueIsValid {

            @BeforeEach
            void setUp() {

                actual = sut.create(inputLoginId, new UserName("ユーザー"), new Password("password"));
            }

            @DisplayName("入力エラーはない")
            @Test
            void assertNotError() {

                assertFalse(actual.getErrors().hasError());
            }

            @DisplayName("対象のユーザが登録されている")
            @Test
            void assertRegistered () {

                final Optional<User> actualOpt = mock.findBy(inputLoginId);
                assertTrue(actualOpt.isPresent());
            }
        }

        @DisplayName("入力内容に不備がある場合")
        @Nested
        class WhenValueIsInvalid {

            @BeforeEach
            void setUp() {

                actual = sut.create(inputLoginId, new UserName(""), new Password("password"));
            }

            @DisplayName("入力エラーがある")
            @Test
            void assertHasError() {

                assertTrue(actual.getErrors().hasError());
            }

            @DisplayName("対象のユーザが登録されていない")
            @Test
            void assertNotRegistered() {

                final Optional<User> actualOpt = mock.findBy(inputLoginId);
                assertFalse(actualOpt.isPresent());
            }
        }

    }
}
