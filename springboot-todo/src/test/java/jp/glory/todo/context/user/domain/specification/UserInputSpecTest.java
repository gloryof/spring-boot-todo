package jp.glory.todo.context.user.domain.specification;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.glory.todo.context.base.domain.error.ErrorInfo;
import jp.glory.todo.context.base.domain.error.ValidateError;
import jp.glory.todo.context.base.domain.error.ValidateErrors;
import jp.glory.todo.context.user.domain.entity.RegisteredUser;
import jp.glory.todo.context.user.domain.repository.RegisteredUserRepository;
import jp.glory.todo.context.user.domain.value.LoginId;
import jp.glory.todo.context.user.domain.value.Password;
import jp.glory.todo.context.user.domain.value.UserId;
import jp.glory.todo.context.user.domain.value.UserName;
import jp.glory.todo.test.validate.ValidateAssert;

class UserInputSpecTest {


    private UserInputSpec sut = null;
    private  ValidateErrors actualErrors = null;
    private RegisteredUserRepository mock = null;

    @BeforeEach
    void setUp() {

        mock = mock(RegisteredUserRepository.class);
    }

    @DisplayName("全ての値が正常に設定されている場合")
    @Nested
    class WhenAllValueIsValid {

        @BeforeEach
        void setUp() {

            final RegisteredUser user = new RegisteredUser(new UserId(1L), new LoginId("test"), new UserName("テストユーザ"),
                    new Password("19CB2A070DDBE8157E17C5DDA0EA38E8AA16FAE1725C1F7AC22747D870368579"));

            sut = new UserInputSpec(user, mock);

            actualErrors = sut.validate();
        }

        @DisplayName("validateを実行しても入力チェックエラーにならない")
        @Test
        void testValidate () {

            assertFalse(actualErrors.hasError());
        }
    }

    @DisplayName("全ての項目が未設定の場合")
    @Nested
    class WhenAllValueIsNotSet {

        @BeforeEach
        void setUp() {

            sut = new UserInputSpec(
                    new RegisteredUser(UserId.notNumberingValue(), LoginId.empty(), UserName.empty(), Password.empty()),
                    mock);
            actualErrors = sut.validate();
        }

        @DisplayName("hasErrorはtrue")
        @Test
        void testValidate () {

            assertTrue(actualErrors.hasError());
        }

        @DisplayName("validateで必須項目がエラーチェックになる")
        @Test
        void assertErrors () {

            final ValidateErrors expectedErrors = new ValidateErrors();

            expectedErrors.add(new ValidateError(ErrorInfo.Required, LoginId.LABEL));
            expectedErrors.add(new ValidateError(ErrorInfo.Required, UserName.LABEL));
            expectedErrors.add(new ValidateError(ErrorInfo.Required, Password.LABEL));

            final ValidateAssert validate = new ValidateAssert(expectedErrors, actualErrors);
            validate.assertAll();
        }
    }

    @DisplayName("既に登録されているログインIDが設定されている場合")
    @Nested
    class WhenLoginIdAlreadyExists {

        private RegisteredUser savedUser = null;

        @BeforeEach
        void setUp() {

            savedUser = new RegisteredUser(new UserId(1l), new LoginId("login-user"), new UserName("ログインユーザ"),
                    new Password("password"));

            when(mock.findBy(any(LoginId.class))).thenReturn(Optional.of(savedUser));
        }

        @DisplayName("別ユーザを同一ログインIDの場合")
        @Nested
        class OtherUserSameLoginId {

            @BeforeEach
            void setUp() {


                final RegisteredUser newUser = new RegisteredUser(new UserId(2l), savedUser.getLoginId(), new UserName("ログインユーザ2"),
                        new Password("password2"));

                sut = new UserInputSpec(newUser, mock);
                actualErrors = sut.validate();
            }

            @DisplayName("hasErrorはtrue")
            @Test
            void testValidate () {

                assertTrue(actualErrors.hasError());
            }

            @DisplayName("重複エラーになる")
            @Test
            void assertErrors () {

                final ValidateErrors expectedErrors = new ValidateErrors();

                expectedErrors.add(new ValidateError(ErrorInfo.LoginIdDuplicated, LoginId.LABEL));

                final ValidateAssert validate = new ValidateAssert(expectedErrors, actualErrors);
                validate.assertAll();
            }
        }


        @DisplayName("別ユーザを同一ログインIDの場合")
        @Nested
        class SameUserSameLoginId {

            @BeforeEach
            void setUp() {

                final RegisteredUser editUser = new RegisteredUser(savedUser.getUserId(), savedUser.getLoginId(), new UserName("ログインユーザ2"),
                        new Password("password2"));

                sut = new UserInputSpec(editUser, mock);
                actualErrors = sut.validate();
            }

            @DisplayName("hasErrorはfalse")
            @Test
            void testValidate () {

                assertFalse(actualErrors.hasError());
            }

        }
    }
}