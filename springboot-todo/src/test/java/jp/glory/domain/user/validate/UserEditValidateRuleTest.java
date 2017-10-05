package jp.glory.domain.user.validate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.glory.domain.common.error.ErrorInfo;
import jp.glory.domain.common.error.ValidateError;
import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.domain.user.entity.User;
import jp.glory.domain.user.repository.UserRepositoryMock;
import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.Password;
import jp.glory.domain.user.value.UserId;
import jp.glory.domain.user.value.UserName;
import jp.glory.test.validate.ValidateAssert;

class UserEditValidateRuleTest {

    private UserEditValidateRule sut = null;
    private ValidateErrors actualErrors =  null;

    @DisplayName("全ての値が正常に設定されている場合")
    @Nested
    class WhenAllValueIsValid {

        @BeforeEach
        void setUp() {

            final User user = new User(new UserId(1L), new LoginId("test"), new UserName("テストユーザ"),
                    new Password("19CB2A070DDBE8157E17C5DDA0EA38E8AA16FAE1725C1F7AC22747D870368579"));

            sut = new UserEditValidateRule(user, new UserRepositoryMock());
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
    class WhenValueIsNotSet {

        private UserEditValidateRule sut = null;

        @BeforeEach
        void setUp() {

            sut = new UserEditValidateRule(
                    new User(UserId.notNumberingValue(), LoginId.empty(), UserName.empty(), Password.empty()),
                    new UserRepositoryMock());
            actualErrors = sut.validate();
        }


        @DisplayName("hasErrorはtrue")
        @Test
        void testValidate () {

            assertTrue(actualErrors.hasError());
        }

        @DisplayName("validateで全ての必須項目がエラーチェックになる")
        @Test
        void assertErrors () {

            final ValidateErrors expectedErrors = new ValidateErrors();

            expectedErrors.add(new ValidateError(ErrorInfo.Required, LoginId.LABEL));
            expectedErrors.add(new ValidateError(ErrorInfo.Required, UserName.LABEL));
            expectedErrors.add(new ValidateError(ErrorInfo.Required, Password.LABEL));
            expectedErrors.add(new ValidateError(ErrorInfo.NotRegister, User.LABEL));

            final ValidateAssert validate = new ValidateAssert(expectedErrors, actualErrors);
            validate.assertAll();
        }
    }
}