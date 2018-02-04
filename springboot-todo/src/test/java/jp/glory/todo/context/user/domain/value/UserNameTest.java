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
import jp.glory.todo.context.user.domain.value.UserName;
import jp.glory.todo.test.util.TestUtil;
import jp.glory.todo.test.validate.ValidateAssert;

class UserNameTest {

    private UserName sut = null;
    private ValidateErrors actualErrors = null;

    @DisplayName("emptyのテスト")
    @Nested
    class TestEmpty {

        @DisplayName("ブランクが返却される")
        @Test
        void returnBalacnk () {

            sut = UserName.empty();

            assertEquals("", sut.getValue());
        }
    }


    @DisplayName("正常な値が設定されている場合")
    @Nested
    class WhenValidValue {

        @BeforeEach
        void setUp() {

            sut = new UserName("テスト");
            actualErrors = sut.validate();
        }

        @DisplayName("validateを行っても入力チェックエラーにならない")
        @Test
        void tetHasError() {

            assertFalse(actualErrors.hasError());
        }
    }

    @DisplayName("値が未設定の場合")
    @Nested
    class WhenValueIsNotSet {

        @BeforeEach
        void setUp() {

            sut = new UserName("");
            actualErrors = sut.validate();
        }

        @DisplayName("validateを行うと入力チェックエラーになる")
        @Test
        void tetHasError() {

            assertTrue(actualErrors.hasError());
        }

        @DisplayName("必須チェックエラーになる")
        @Test
        void assertErrors() {

            final ValidateError expectedError = new ValidateError(ErrorInfo.Required, UserName.LABEL);

            final ValidateAssert validate = new ValidateAssert(expectedError, actualErrors);
            validate.assertAll();
        }
    }

    @DisplayName("値が50文字の場合")
    @Nested
    class WhenLengthMax {

        @BeforeEach
        void setUp() {

            sut = new UserName(TestUtil.repeat("a", 50));
            actualErrors = sut.validate();
        }

        @DisplayName("validateを行っても入力チェックエラーにならない")
        @Test
        void tetHasError() {

            assertFalse(actualErrors.hasError());
        }
    }

    @DisplayName("値が51文字以上の場合")
    @Nested
    class WhenLengthMaxOver {

        @BeforeEach
        void setUp() {

            sut = new UserName(TestUtil.repeat("a", 51));
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

            final ValidateError expectedError = new ValidateError(ErrorInfo.MaxLengthOver, UserName.LABEL, "50");

            final ValidateAssert validate = new ValidateAssert(expectedError, actualErrors);
            validate.assertAll();
        }
    }
}