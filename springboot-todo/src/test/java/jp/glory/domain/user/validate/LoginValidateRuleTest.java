package jp.glory.domain.user.validate;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import jp.glory.domain.common.error.ErrorInfo;
import jp.glory.domain.common.error.ValidateError;
import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.domain.user.entity.User;
import jp.glory.domain.user.repository.UserRepositoryMock;
import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.Password;
import jp.glory.domain.user.value.UserId;
import jp.glory.domain.user.value.UserName;
import jp.glory.test.validate.ValidateErrorsHelper;

@RunWith(Enclosed.class)
public class LoginValidateRuleTest {

    public static class 全ての値が正常に設定されている場合 {

        private LoginValidateRule sut = null;
        private ValidateErrors actualErrors = null;

        @Before
        public void setUp() {

            final LoginId loginId = new LoginId("test-user");
            final Password password = new Password("test-password");
            final UserRepositoryMock mock = new UserRepositoryMock();
            mock.save(new User(new UserId(1l), loginId, new UserName("テスト"), password));

            sut = new LoginValidateRule(loginId, password, mock);
            actualErrors = sut.validate();
        }

        @Test
        public void validateを実行しても入力チェックエラーにならない() {

            Assert.assertThat(actualErrors.hasError(), CoreMatchers.is(false));
        }
    }

    public static class 全ての項目が未設定の場合 {

        private LoginValidateRule sut = null;
        private ValidateErrors actualErrors = null;

        @Before
        public void setUp() {

            final LoginId loginId = new LoginId("");
            final Password password = new Password("");

            sut = new LoginValidateRule(loginId, password, new UserRepositoryMock());
            actualErrors = sut.validate();
        }

        @Test
        public void validateを実行したら入力チェックエラーになる() {

            Assert.assertThat(actualErrors.hasError(), CoreMatchers.is(true));

            final List<ValidateError> errorList = new ArrayList<>();

            errorList.add(new ValidateError(ErrorInfo.Required, LoginId.LABEL));
            errorList.add(new ValidateError(ErrorInfo.Required, Password.LABEL));

            final ValidateErrorsHelper helper = new ValidateErrorsHelper(actualErrors);
            helper.assertErrors(errorList);
        }
    }

    public static class ログインIDとパスワードが一致していない場合 {

        private LoginValidateRule sut = null;
        private ValidateErrors actualErrors = null;

        @Before
        public void setUp() {

            final LoginId loginId = new LoginId("test-user");
            final Password password = new Password("test-password");
            final UserRepositoryMock mock = new UserRepositoryMock();
            mock.save(new User(new UserId(1l), loginId, new UserName("テスト"), password));

            sut = new LoginValidateRule(loginId, new Password("not-match-password"), mock);
            actualErrors = sut.validate();
        }

        @Test
        public void validateを実行したら入力チェックエラーになる() {

            Assert.assertThat(actualErrors.hasError(), CoreMatchers.is(true));

            final List<ValidateError> errorList = new ArrayList<>();

            errorList.add(new ValidateError(ErrorInfo.LoginFailed));

            final ValidateErrorsHelper helper = new ValidateErrorsHelper(actualErrors);
            helper.assertErrors(errorList);
        }
    }
}