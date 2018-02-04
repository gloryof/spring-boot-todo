package jp.glory.todo.context.base.domain.type;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.glory.todo.context.base.domain.annotation.MaxLength;
import jp.glory.todo.context.base.domain.annotation.Required;
import jp.glory.todo.context.base.domain.annotation.ValidCharacters;
import jp.glory.todo.context.base.domain.annotation.param.ValidCharcterType;
import jp.glory.todo.context.base.domain.error.ErrorInfo;
import jp.glory.todo.context.base.domain.error.ValidateError;
import jp.glory.todo.context.base.domain.error.ValidateErrors;
import jp.glory.todo.context.base.domain.type.InputText;
import jp.glory.todo.test.validate.ValidateAssert;

class InputTextTest {

    private static final String LABEL = "テスト";

    private static class StubClass extends InputText {

        private static final long serialVersionUID = 1L;

        public StubClass(final String paramValue) {

            super(paramValue);
        }
    }

    @Required(label = LABEL)
    @MaxLength(value = 4, label = LABEL, isActive = false)
    @ValidCharacters(value = ValidCharcterType.OnlySingleByteChars, label = LABEL, isActive = false)
    private static class RequiredClass extends StubClass {

        private static final long serialVersionUID = 1L;

        public RequiredClass(String paramValue) {
            super(paramValue);
        }
        
    }

    @Required(label = LABEL, isActive = false)
    @MaxLength(value = 4, label = LABEL)
    @ValidCharacters(value = ValidCharcterType.OnlySingleByteChars, label = LABEL, isActive = false)
    private static class MaxLengthClass extends StubClass {

        private static final long serialVersionUID = 1L;

        public MaxLengthClass(final String paramValue) {

            super(paramValue);
        }
    }

    @Required(label = LABEL, isActive = false)
    @MaxLength(value = 4, label = LABEL, isActive = false)
    @ValidCharacters(value = ValidCharcterType.OnlySingleByteChars, label = LABEL)
    private static class ValidCharClass extends StubClass {

        private static final long serialVersionUID = 1L;

        public ValidCharClass(final String paramValue) {

            super(paramValue);
        }
    }

    private StubClass sut = null;

    private static final String INIT_VALUE = "タイトルテスト";

    @DisplayName("必須項目で初期値に値が設定されている場合")
    @Nested
    class WhenSetRequiredOnly {

        @BeforeEach
        public void setUp() {

            sut = new RequiredClass(INIT_VALUE);
        }

        @DisplayName("valueには初期値が設定されている")
        @Test
        public void testGetValue() {

            assertEquals(INIT_VALUE, sut.getValue());
        }

        @DisplayName("validateで入力チェックエラーにならない")
        @Test
        public void testValidate() {

            final ValidateErrors actualErrors = sut.validate();

            assertFalse(actualErrors.hasError());
        }

        @DisplayName("必須項目で初期値にNullが設定されている場合")
        @Nested
        class  WhenInitValueIsNull {

            @BeforeEach
            public void setUp() {

                sut = new RequiredClass(null);
            }

            @DisplayName("valueにはブランクが設定されている")
            @Test
            public void testGetValue() {

                assertEquals("", sut.getValue());
            }

            @DisplayName("validateを行うと必須チェックエラーになる")
            @Test
            public void testValidate() {

                final ValidateErrors actualErrors = sut.validate();

                assertTrue(actualErrors.hasError());

                final ValidateError expectedError = new ValidateError(ErrorInfo.Required, LABEL);

                ValidateAssert validate = new ValidateAssert(expectedError, actualErrors);
                validate.assertAll();
            }
        }
    }

    @DisplayName("必須項目で初期値にブランクが設定されている場合")
    @Nested
    class WhenSetBlank {

        @BeforeEach
        public void setUp() {

            sut = new RequiredClass("");
        }

        @DisplayName("valueにはブランクが設定されている")
        @Test
        public void testGetValue() {

            assertEquals("", sut.getValue());
        }

        @DisplayName("validateを行うと必須チェックエラーになる")
        @Test
        public void testValidate() {

            final ValidateErrors actualErrors = sut.validate();

            assertTrue(actualErrors.hasError());

            final ValidateError expectedError = new ValidateError(ErrorInfo.Required, LABEL);
            ValidateAssert validate = new ValidateAssert(expectedError, actualErrors);
            validate.assertAll();
        }
    }

    @DisplayName("最大文字数のテスト")
    @Nested
    class TestMaxLength {

        @DisplayName("最大文字数以内の場合入力チェックエラーにならない")
        @Test
        public void notOver() {

            final StubClass sut =  new MaxLengthClass("１２３４");

            final ValidateErrors actualErrors = sut.validate();

            assertFalse(actualErrors.hasError());
        }

        @DisplayName("最大文字数を超えていて場合入力チェックエラーになる")
        @Test
        public void over() {

            final StubClass sut =  new MaxLengthClass("１２３４５");

            final ValidateErrors actualErrors = sut.validate();

            assertTrue(actualErrors.hasError());

            final ValidateError expectedError = new ValidateError(ErrorInfo.MaxLengthOver, LABEL, "4");
            ValidateAssert validate = new ValidateAssert(expectedError, actualErrors);
            validate.assertAll();
        }
    }

    @DisplayName("許可文字のテスト")
    @Nested
    class AllowedCharacters {

        @DisplayName("許可文字列のみの場合入力チェックエラーにならない")
        @Test
        public void onlyAllowed() {

            final StubClass sut = new ValidCharClass("123456789abcABC!#$%&'()");

            final ValidateErrors actualErrors = sut.validate();

            assertFalse(actualErrors.hasError());
        }

        @DisplayName("許可文字列以外を含む場合は入力チェックエラーになる")
        @Test
        public void notAllowed() {

            final StubClass sut = new ValidCharClass("1234あ5");

            final ValidateErrors actualErrors = sut.validate();

            assertTrue(actualErrors.hasError());

            final ValidateError expectedError = new ValidateError(ErrorInfo.InvalidCharacters, LABEL);
            ValidateAssert validate = new ValidateAssert(expectedError, actualErrors);
            validate.assertAll();
        }
    }
}
