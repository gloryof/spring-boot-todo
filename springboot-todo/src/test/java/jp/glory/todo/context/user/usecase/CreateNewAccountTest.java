package jp.glory.todo.context.user.usecase;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.glory.todo.context.user.domain.entity.RegisteredUser;
import jp.glory.todo.context.user.domain.repository.RegisteredUserRepository;
import jp.glory.todo.context.user.domain.value.LoginId;
import jp.glory.todo.context.user.domain.value.Password;
import jp.glory.todo.context.user.domain.value.UserName;

class CreateNewAccountTest {

    private CreateNewAccount sut = null;
    private RegisteredUserRepository mock = null;
    private LoginId inputLoginId = null;
    private CreateNewAccount.Result actual = null;

    @BeforeEach
    void setUp() {

        mock = mock(RegisteredUserRepository.class);
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

                verify(mock, times(1)).save(any(RegisteredUser.class));
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

                final Optional<RegisteredUser> actualOpt = mock.findBy(inputLoginId);
                assertFalse(actualOpt.isPresent());
            }
        }

    }
}
