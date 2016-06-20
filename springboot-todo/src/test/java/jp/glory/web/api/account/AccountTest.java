package jp.glory.web.api.account;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jp.glory.domain.common.error.ErrorInfo;
import jp.glory.domain.common.error.ValidateError;
import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.Password;
import jp.glory.domain.user.value.UserName;
import jp.glory.framework.web.exception.InvalidRequestException;
import jp.glory.infra.encryption.Encryption;
import jp.glory.usecase.user.CreateNewAccount;
import jp.glory.usecase.user.CreateNewAccount.Result;
import jp.glory.web.api.account.request.NewAccountRequest;

@RunWith(Enclosed.class)
public class AccountTest {

    @RunWith(Enclosed.class)
    public static class createのテスト {

        public static class 入力値が正常な場合 {

            private Account sut = null;

            private CreateNewAccount mockCreateAccount = null;
            private Encryption mockEncryption = null;
            private CreateNewAccount.Result mockUseCaseResult = null;

            private LoginId expectedLoginId = null;
            private UserName expectedUserName = null;
            private Password expectedPassword = null;

            private ResponseEntity<Object> actual = null;
            private LoginId actualLoginId = null;
            private UserName actualUserName = null;
            private Password actualPassword = null;

            @Before
            public void setUp() {

                mockCreateAccount = Mockito.mock(CreateNewAccount.class);
                mockEncryption = Mockito.mock(Encryption.class);
                mockUseCaseResult = Mockito.mock(CreateNewAccount.Result.class);

                expectedLoginId = new LoginId("test-login");
                expectedUserName = new UserName("テストユーザ");
                expectedPassword = new Password("test-password");

                Mockito.when(mockEncryption.encrypt(Mockito.anyString())).thenReturn(expectedPassword);

                Mockito.when(mockUseCaseResult.getErrors()).thenReturn(new ValidateErrors());
                Mockito
                    .when(mockCreateAccount.create(Mockito.any(), Mockito.any(), Mockito.any()))
                    .then(new Answer<CreateNewAccount.Result>() {

                        @Override
                        public Result answer(InvocationOnMock invocation) throws Throwable {

                            actualLoginId = invocation.getArgumentAt(0, LoginId.class);
                            actualUserName = invocation.getArgumentAt(1, UserName.class);
                            actualPassword = invocation.getArgumentAt(2, Password.class);
                            return mockUseCaseResult;
                        }
                    });

                sut = new Account(mockCreateAccount, mockEncryption);

                final NewAccountRequest request = new NewAccountRequest();
                request.setLoginId(expectedLoginId.getValue());
                request.setUserName(expectedUserName.getValue());
                request.setPassword(expectedPassword.getValue());

                actual = sut.create(request);
            }

            @Test
            public void ステータスはCREATED() {

                assertThat(actual.getStatusCode(), is(HttpStatus.CREATED));
            }

            @Test
            public void 設定された内容が保存される()  {

                assertThat(actualLoginId.getValue(), is(expectedLoginId.getValue()));
                assertThat(actualUserName.getValue(), is(expectedUserName.getValue()));
                assertThat(actualPassword.getValue(), is(expectedPassword.getValue()));
            }
        }

        public static class 入力値が不正な場合 {

            private Account sut = null;

            private CreateNewAccount mockCreateAccount = null;
            private Encryption mockEncryption = null;
            private CreateNewAccount.Result mockUseCaseResult = null;

            private ValidateErrors expectedErrors = null;

            @Before
            public void setUp() {

                mockCreateAccount = Mockito.mock(CreateNewAccount.class);
                mockEncryption = Mockito.mock(Encryption.class);
                mockUseCaseResult = Mockito.mock(CreateNewAccount.Result.class);

                expectedErrors = new ValidateErrors();
                expectedErrors.add(new ValidateError(ErrorInfo.Required, LoginId.LABEL));
                expectedErrors.add(new ValidateError(ErrorInfo.Required, UserName.LABEL));
                expectedErrors.add(new ValidateError(ErrorInfo.Required, Password.LABEL));

                Mockito.when(mockEncryption.encrypt(Mockito.anyString())).thenReturn(Password.empty());
                Mockito.when(mockUseCaseResult.getErrors()).thenReturn(expectedErrors);
                Mockito
                    .when(mockCreateAccount.create(Mockito.any(), Mockito.any(), Mockito.any()))
                    .thenReturn(mockUseCaseResult);

                sut = new Account(mockCreateAccount, mockEncryption);
            }

            @Test
            public void InvalidRequestExceptionがスローされる() {

                try {

                    sut.create(new NewAccountRequest());
                    fail();
                } catch (final InvalidRequestException exception) {

                    final ValidateErrors actualErrors = exception.getErrors();

                    for (int i = 0; i < actualErrors.toList().size(); i++) {

                        final String actualMessage = actualErrors.toList().get(i).getMessage();
                        final String expectedMessage = expectedErrors.toList().get(i).getMessage();
                        assertThat(actualMessage, is(expectedMessage));
                    }
                }
            }
        }
    }

}
