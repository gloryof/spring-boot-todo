package jp.glory.todo.context.user.domain.validate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.glory.todo.context.base.domain.error.ErrorInfo;
import jp.glory.todo.context.base.domain.error.ValidateError;
import jp.glory.todo.context.base.domain.error.ValidateErrors;
import jp.glory.todo.context.user.domain.entity.User;
import jp.glory.todo.context.user.domain.repository.UserRepositoryMock;
import jp.glory.todo.context.user.domain.validate.UserModifyCommonValidateRule;
import jp.glory.todo.context.user.domain.value.LoginId;
import jp.glory.todo.context.user.domain.value.Password;
import jp.glory.todo.context.user.domain.value.UserId;
import jp.glory.todo.context.user.domain.value.UserName;
import jp.glory.todo.test.validate.ValidateAssert;

class UserModifyCommonValidateRuleTest {


    private UserModifyCommonValidateRule sut = null;
    private  ValidateErrors actualErrors = null;
    private UserRepositoryMock stub = null;


    @DisplayName("全ての値が正常に設定されている場合")
    @Nested
    class WhenAllValueIsValid {

        @BeforeEach
        void setUp() {

            final User user = new User(new UserId(1L), new LoginId("test"), new UserName("テストユーザ"),
                    new Password("19CB2A070DDBE8157E17C5DDA0EA38E8AA16FAE1725C1F7AC22747D870368579"));

            sut = new UserModifyCommonValidateRule(user, new UserRepositoryMock());

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

            sut = new UserModifyCommonValidateRule(
                    new User(UserId.notNumberingValue(), LoginId.empty(), UserName.empty(), Password.empty()),
                    new UserRepositoryMock());
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

        @BeforeEach
        void setUp() {

            stub = new UserRepositoryMock();

            final User savedUser = new User(new UserId(1l), new LoginId("login-user"), new UserName("ログインユーザ"),
                    new Password("password"));

            stub.save(savedUser);
        }

        @DisplayName("別ユーザを同一ログインIDの場合")
        @Nested
        class OtherUserSameLoginId {

            @BeforeEach
            void setUp() {

                final User savedUser = stub.findAll().get(0);

                final User newUser = new User(new UserId(2l), savedUser.getLoginId(), new UserName("ログインユーザ2"),
                        new Password("password2"));

                sut = new UserModifyCommonValidateRule(newUser, stub);
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

                final User savedUser = stub.findAll().get(0);
                final User editUser = new User(savedUser.getUserId(), savedUser.getLoginId(), new UserName("ログインユーザ2"),
                        new Password("password2"));

                sut = new UserModifyCommonValidateRule(editUser, stub);
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