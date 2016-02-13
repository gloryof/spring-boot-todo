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
public class UserEditValidateRuleTest {

    public static class 全ての値が正常に設定されている場合 {

        private UserEditValidateRule sut = null;

        @Before
        public void setUp() {

            final User user = new User(new UserId(1L), new LoginId("test"), new UserName("テストユーザ"),
                    new Password("19CB2A070DDBE8157E17C5DDA0EA38E8AA16FAE1725C1F7AC22747D870368579"));

            sut = new UserEditValidateRule(user, new UserRepositoryMock());
        }

        @Test
        public void validateを実行しても入力チェックエラーにならない() {

            final ValidateErrors actualErrors = sut.validate();

            Assert.assertThat(actualErrors.hasError(), CoreMatchers.is(false));
        }
    }

    public static class 全ての項目が未設定の場合 {

        private UserEditValidateRule sut = null;

        @Before
        public void setUp() {

            sut = new UserEditValidateRule(
                    new User(UserId.notNumberingValue(), LoginId.empty(), UserName.empty(), Password.empty()),
                    new UserRepositoryMock());
        }

        @Test
        public void validateで全ての必須項目がエラーチェックになる() {

            final ValidateErrors actual = sut.validate();

            Assert.assertThat(actual.hasError(), CoreMatchers.is(true));

            final List<ValidateError> errorList = new ArrayList<>();

            errorList.add(new ValidateError(ErrorInfo.NotRegister, User.LABEL));
            errorList.add(new ValidateError(ErrorInfo.Required, UserName.LABEL));
            errorList.add(new ValidateError(ErrorInfo.Required, LoginId.LABEL));
            errorList.add(new ValidateError(ErrorInfo.Required, Password.LABEL));

            final ValidateErrorsHelper helper = new ValidateErrorsHelper(actual);
            helper.assertErrors(errorList);
        }
    }
}