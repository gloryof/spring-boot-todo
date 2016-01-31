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
import jp.glory.domain.user.repository.UserRepositoryStub;
import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.Password;
import jp.glory.domain.user.value.UserId;
import jp.glory.domain.user.value.UserName;
import jp.glory.test.validate.ValidateErrorsHelper;

@RunWith(Enclosed.class)
public class UserModifyCommonValidateRuleTest {

    public static class 全ての値が正常に設定されている場合 {

        private UserModifyCommonValidateRule sut = null;

        @Before
        public void setUp() {

            final User user = new User(new UserId(1L), new LoginId("test"), new UserName("テストユーザ"),
                    new Password("19CB2A070DDBE8157E17C5DDA0EA38E8AA16FAE1725C1F7AC22747D870368579"));

            sut = new UserModifyCommonValidateRule(user, new UserRepositoryStub());
        }

        @Test
        public void validateを実行しても入力チェックエラーにならない() {

            final ValidateErrors actualErrors = sut.validate();

            Assert.assertThat(actualErrors.hasError(), CoreMatchers.is(false));
        }
    }

    public static class 全ての項目が未設定の場合 {

        private UserModifyCommonValidateRule sut = null;

        @Before
        public void setUp() {

            sut = new UserModifyCommonValidateRule(
                    new User(UserId.notNumberingValue(), LoginId.empty(), UserName.empty(), Password.empty()),
                    new UserRepositoryStub());
        }

        @Test
        public void validateで必須項目がエラーチェックになる() {

            final ValidateErrors actual = sut.validate();

            Assert.assertThat(actual.hasError(), CoreMatchers.is(true));

            final List<ValidateError> errorList = new ArrayList<>();

            errorList.add(new ValidateError(ErrorInfo.Required, UserName.LABEL));
            errorList.add(new ValidateError(ErrorInfo.Required, LoginId.LABEL));
            errorList.add(new ValidateError(ErrorInfo.Required, Password.LABEL));

            final ValidateErrorsHelper helper = new ValidateErrorsHelper(actual);
            helper.assertErrors(errorList);
        }
    }

    public static class 既に登録されているログインIDが設定されている場合 {

        private UserModifyCommonValidateRule sut = null;
        private UserRepositoryStub stub = null;

        @Before
        public void setUp() {

            stub = new UserRepositoryStub();

            final User savedUser = new User(new UserId(1l), new LoginId("login-user"), new UserName("ログインユーザ"),
                    new Password("password"));

            stub.save(savedUser);
        }

        @Test
        public void 別ユーザを同一ログインIDはエラー() {

            final User savedUser = stub.findAll().get(0);

            final User newUser = new User(new UserId(2l), savedUser.getLoginId(), new UserName("ログインユーザ2"),
                    new Password("password2"));

            sut = new UserModifyCommonValidateRule(newUser, stub);

            final ValidateErrors actual = sut.validate();

            Assert.assertThat(actual.hasError(), CoreMatchers.is(true));
            Assert.assertThat(actual.toList().size(), CoreMatchers.is(1));

            final List<ValidateError> errorList = new ArrayList<>();
            errorList.add(new ValidateError(ErrorInfo.LoginIdDuplicated, LoginId.LABEL));

            final ValidateErrorsHelper helper = new ValidateErrorsHelper(actual);
            helper.assertErrors(errorList);
        }

        @Test
        public void 同一ユーザを同一ログインIDはエラーにならない() {

            final User savedUser = stub.findAll().get(0);
            final User editUser = new User(savedUser.getUserId(), savedUser.getLoginId(), new UserName("ログインユーザ2"),
                    new Password("password2"));

            sut = new UserModifyCommonValidateRule(editUser, stub);

            final ValidateErrors actual = sut.validate();

            Assert.assertThat(actual.hasError(), CoreMatchers.is(false));
        }
    }
}