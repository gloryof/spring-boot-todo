package jp.glory.domain.todo.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.glory.domain.common.error.ErrorInfo;
import jp.glory.domain.common.error.ValidateError;
import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.test.util.TestUtil;
import jp.glory.test.validate.ValidateAssert;

class MemoTest {

    private Memo sut = null;
    private ValidateErrors actualErrors = null;

    @DisplayName("emptyのテスト")
    @Nested
    class TestEmpty {

        @DisplayName("ブランクが返る")
        @Test
        void returnBlank() {

            sut = Memo.empty();

            assertEquals("", sut.getValue());
        }
    }

    @DisplayName("正常な値が設定されている場合")
    @Nested
    class WhenValidValue {

        @BeforeEach
        void setUp() {

            sut = new Memo("テスト");
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

        private Memo sut = null;

        @BeforeEach
        void setUp() {

            sut = new Memo("");
            actualErrors = sut.validate();
        }

        @DisplayName("validateを行っても入力チェックエラーにならない")
        @Test
        void tetHasError() {

            assertFalse(actualErrors.hasError());
        }
    }

    @DisplayName("値が1000文字の場合")
    @Nested
    class WhenLengthMax {

        private Memo sut = null;

        @BeforeEach
        void setUp() {

            sut = new Memo(TestUtil.repeat("a", 1000));
            actualErrors = sut.validate();
        }

        @DisplayName("validateを行っても入力チェックエラーにならない")
        @Test
        void tetHasError() {

            assertFalse(actualErrors.hasError());
        }
    }

    @DisplayName("値が1001文字以上の場合")
    @Nested
    class WhenLengthMaxOver {

        private Memo sut = null;

        @BeforeEach
        void setUp() {

            sut = new Memo(TestUtil.repeat("a", 1001));
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

            final ValidateError expectedError = new ValidateError(ErrorInfo.MaxLengthOver, Memo.LABEL, "1,000");

            final ValidateAssert validate = new ValidateAssert(expectedError, actualErrors);
            validate.assertAll();
        }
    }
}