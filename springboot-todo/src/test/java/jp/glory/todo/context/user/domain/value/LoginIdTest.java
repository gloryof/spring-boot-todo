package jp.glory.todo.context.user.domain.value;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.glory.todo.context.base.domain.error.ErrorInfo;
import jp.glory.todo.context.base.domain.error.ValidateError;
import jp.glory.todo.context.base.domain.error.ValidateErrors;
import jp.glory.todo.context.user.domain.value.LoginId;
import jp.glory.todo.test.util.TestUtil;
import jp.glory.todo.test.validate.ValidateAssert;

class LoginIdTest {

    private LoginId sut = null;
    private ValidateErrors actualErrors = null;

    @DisplayName("emptyのテスト")
    @Nested
    class TestEmpty {

        @DisplayName("ブランクが返る")
        @Test
        void returnBlank() {

            sut = LoginId.empty();

            assertEquals("", sut.getValue());
        }
    }

    @DisplayName("正常な値が設定されている場合")
    @Nested
    class WhenValidValue {

        @BeforeEach
        void setUp() {

            sut = new LoginId("tes-user");
            actualErrors = sut.validate();
        }

        @DisplayName("validateを行っても入力チェックエラーにならない")
        @Test
        void assertHasError() {

            assertFalse(actualErrors.hasError());
        }
    }

    @DisplayName("値が未設定の場合")
    @Nested
    class WhenValueIsNotSet {


        @BeforeEach
        void setUp() {

            sut = new LoginId("");
            actualErrors = sut.validate();
        }

        @DisplayName("validateを行うと入力チェックエラーになる")
        @Test
        void assertHasError() {

            assertTrue(actualErrors.hasError());
        }

        @DisplayName("必須チェックエラーになる")
        @Test
        void assertErrors() {

            final ValidateError expectedError = new ValidateError(ErrorInfo.Required, LoginId.LABEL);

            final ValidateAssert validate = new ValidateAssert(expectedError, actualErrors);
            validate.assertAll();
        }
    }

    @DisplayName("値が50文字の場合")
    @Nested
    class WhenLengthMax {

        @BeforeEach
        void setUp() {

            sut = new LoginId(TestUtil.repeat("a", 50));
            actualErrors = sut.validate();
        }

        @DisplayName("validateを行っても入力チェックエラーにならない")
        @Test
        void assertHasError() {

            assertFalse(actualErrors.hasError());
        }
    }


    @DisplayName("値が51文字以上の場合")
    @Nested
    class WhenLengthMaxOver {

        @BeforeEach
        void setUp() {

            sut = new LoginId(TestUtil.repeat("a", 51));
            actualErrors = sut.validate();
        }

        @DisplayName("validateを行うと入力チェックエラーになる")
        @Test
        void tetHasError() {

            assertTrue(actualErrors.hasError());
        }

        @DisplayName("文字数オーバーエラーになる")
        @Test
        void assertErrors() {

            final ValidateError expectedError = new ValidateError(ErrorInfo.MaxLengthOver, LoginId.LABEL, "50");

            final ValidateAssert validate = new ValidateAssert(expectedError, actualErrors);
            validate.assertAll();
        }
    }

    @DisplayName("値に使用できない文字のみ設定されている場合")
    @Nested
    class WhenInvalidCharacters {

        @BeforeEach
        void setUp() {

            sut = new LoginId("あ");
            actualErrors = sut.validate();
        }


        @DisplayName("validateを行うと入力チェックエラーになる")
        @Test
        void tetHasError() {

            assertTrue(actualErrors.hasError());
        }

        @DisplayName("文字種エラーになる")
        @Test
        void assertErrors() {


            final ValidateError expectedError = new ValidateError(ErrorInfo.InvalidCharacters, LoginId.LABEL);

            final ValidateAssert validate = new ValidateAssert(expectedError, actualErrors);
            validate.assertAll();
        }
    }
}
