package jp.glory.domain.user.validate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

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

class LoginValidateRuleTest {

    private LoginValidateRule sut = null;
    private ValidateErrors actualErrors = null;

    @DisplayName("全ての値が正常に設定されている場合")
    @Nested
    class WhenAllValueIsValid {

        @BeforeEach
        void setUp() {

            final LoginId loginId = new LoginId("test-user");
            final Password password = new Password("test-password");
            final UserRepositoryMock mock = new UserRepositoryMock();
            mock.save(new User(new UserId(1l), loginId, new UserName("テスト"), password));

            sut = new LoginValidateRule(loginId, password, mock);
            actualErrors = sut.validate();
        }

        @DisplayName("hasErrorはfalse")
        @Test
        void testHasError() {

            assertFalse(actualErrors.hasError());
        }
    }

    @DisplayName("全ての項目が未設定の場合")
    @Nested
    class WhenAllValueIsNotSet {

        @BeforeEach
        void setUp() {

            final LoginId loginId = new LoginId("");
            final Password password = new Password("");

            sut = new LoginValidateRule(loginId, password, new UserRepositoryMock());
            actualErrors = sut.validate();
        }

        @DisplayName("hasErrorはtrue")
        @Test
        void testHasError() {

            assertTrue(actualErrors.hasError());
        }

        @Description("エラー情報が設定される")
        @Test
        void assertErrors() {

            final ValidateErrors expectedErrors = new ValidateErrors();

            expectedErrors.add(new ValidateError(ErrorInfo.Required, LoginId.LABEL));
            expectedErrors.add(new ValidateError(ErrorInfo.Required, Password.LABEL));

            final ValidateAssert validate = new ValidateAssert(expectedErrors, actualErrors);
            validate.assertAll();
        }
    }

    @DisplayName("ログインIDとパスワードが一致していない場合")
    @Nested
    class WhenLoginInfoMismatch {

        @BeforeEach
        void setUp() {

            final LoginId loginId = new LoginId("test-user");
            final Password password = new Password("test-password");
            final UserRepositoryMock mock = new UserRepositoryMock();
            mock.save(new User(new UserId(1l), loginId, new UserName("テスト"), password));

            sut = new LoginValidateRule(loginId, new Password("not-match-password"), mock);
            actualErrors = sut.validate();
        }


        @DisplayName("hasErrorはtrue")
        @Test
        void testHasError() {

            assertTrue(actualErrors.hasError());
        }

        @Description("認証エラーが設定される")
        @Test
        void assertErrors() {

            final ValidateErrors expectedErrors = new ValidateErrors();
            expectedErrors.add(new ValidateError(ErrorInfo.LoginFailed));

            final ValidateAssert validate = new ValidateAssert(expectedErrors, actualErrors);
            validate.assertAll();
        }
   }
}