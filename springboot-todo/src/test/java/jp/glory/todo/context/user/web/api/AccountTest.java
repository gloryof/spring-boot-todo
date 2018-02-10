package jp.glory.todo.context.user.web.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jp.glory.todo.context.base.domain.error.ErrorInfo;
import jp.glory.todo.context.base.domain.error.ValidateError;
import jp.glory.todo.context.base.domain.error.ValidateErrors;
import jp.glory.todo.context.base.web.exception.InvalidRequestException;
import jp.glory.todo.context.user.domain.value.Encryption;
import jp.glory.todo.context.user.domain.value.LoginId;
import jp.glory.todo.context.user.domain.value.Password;
import jp.glory.todo.context.user.domain.value.UserName;
import jp.glory.todo.context.user.usecase.CreateNewAccount;
import jp.glory.todo.context.user.usecase.CreateNewAccount.Result;
import jp.glory.todo.context.user.web.api.Account;
import jp.glory.todo.context.user.web.api.request.NewAccountRequest;

class AccountTest {


    private Account sut = null;

    private CreateNewAccount mockCreateAccount = null;
    private Encryption mockEncryption = null;
    private CreateNewAccount.Result mockUseCaseResult = null;

    @BeforeEach
    void setUp() {

        mockCreateAccount = Mockito.mock(CreateNewAccount.class);
        mockEncryption = Mockito.mock(Encryption.class);
        mockUseCaseResult = Mockito.mock(CreateNewAccount.Result.class);
    }

    @DisplayName("createのテスト")
    @Nested
    class Create {

        @DisplayName("入力値が正常な場合")
        @Nested
        class WhenValueIsValid {

            private LoginId expectedLoginId = null;
            private UserName expectedUserName = null;
            private Password expectedPassword = null;

            private ResponseEntity<Object> actual = null;
            private LoginId actualLoginId = null;
            private UserName actualUserName = null;
            private Password actualPassword = null;

            @BeforeEach
            void setUp() {

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

            @DisplayName("ステータスはCREATED")
            @Test
            void assertStatusCode () {

                assertEquals(HttpStatus.CREATED, actual.getStatusCode());
            }

            @DisplayName("設定された内容が保存される")
            @Test
            void assertSavedValues() {

                assertEquals(expectedLoginId.getValue(), actualLoginId.getValue());
                assertEquals(expectedUserName.getValue(), actualUserName.getValue());
                assertEquals(expectedPassword.getValue(), actualPassword.getValue());
            }
        }

        @DisplayName("入力値が不正な場合")
        @Nested
        class WhenValueIsInvalid {

            private ValidateErrors expectedErrors = null;

            @BeforeEach
            void setUp() {

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

            @DisplayName("InvalidRequestExceptionがスローされる")
            @Test
            void assertThrowException () {

                final InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                        () -> sut.create(new NewAccountRequest()));

                        final ValidateErrors actualErrors = exception.getErrors();

                for (int i = 0; i < actualErrors.toList().size(); i++) {

                    final String actualMessage = actualErrors.toList().get(i).getMessage();
                    final String expectedMessage = expectedErrors.toList().get(i).getMessage();
                    assertEquals(expectedMessage, actualMessage);
                }
            }
        }
    }

}
