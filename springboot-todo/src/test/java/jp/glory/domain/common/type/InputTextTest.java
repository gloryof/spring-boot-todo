package jp.glory.domain.common.type;

import java.util.List;
import jp.glory.domain.common.annotation.MaxLength;
import jp.glory.domain.common.annotation.Required;
import jp.glory.domain.common.annotation.ValidCharacters;
import jp.glory.domain.common.annotation.param.ValidCharcterType;
import jp.glory.domain.common.error.ErrorInfo;
import jp.glory.domain.common.error.ValidateError;
import jp.glory.domain.common.error.ValidateErrors;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static jp.glory.test.validate.ValidateMatcher.validatedBy;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Enclosed.class)
public class InputTextTest {

    private static final String LABEL = "テスト";

    private static class StubClass extends InputText {

        public StubClass(final String paramValue) {

            super(paramValue);
        }
    }

    @Required(label = LABEL)
    @MaxLength(value = 4, label = LABEL, isActive = false)
    @ValidCharacters(value = ValidCharcterType.OnlySingleByteChars, label = LABEL, isActive = false)
    private static class RequiredClass extends StubClass {

        public RequiredClass(String paramValue) {
            super(paramValue);
        }
        
    }

    @Required(label = LABEL, isActive = false)
    @MaxLength(value = 4, label = LABEL)
    @ValidCharacters(value = ValidCharcterType.OnlySingleByteChars, label = LABEL, isActive = false)
    private static class MaxLengthClass extends StubClass {

        public MaxLengthClass(final String paramValue) {

            super(paramValue);
        }
    }

    @Required(label = LABEL, isActive = false)
    @MaxLength(value = 4, label = LABEL, isActive = false)
    @ValidCharacters(value = ValidCharcterType.OnlySingleByteChars, label = LABEL)
    private static class ValidCharClass extends StubClass {

        public ValidCharClass(final String paramValue) {

            super(paramValue);
        }
    }

    public static class 必須項目で初期値に値が設定されている場合 {

        private StubClass sut = null;

        private static final String INIT_VALUE = "タイトルテスト";

        @Before
        public void setUp() {

            sut = new RequiredClass(INIT_VALUE);
        }

        @Test
        public void valueには初期値が設定されている() {

            assertThat(sut.getValue(), is(INIT_VALUE));
        }

        @Test
        public void validateで入力チェックエラーにならない() {

            final ValidateErrors actualErrors = sut.validate();

            assertThat(actualErrors.hasError(), is(false));
        }
    }

    public static class 必須項目で初期値にNullが設定されている場合 {

        private StubClass sut = null;

        private static final String INIT_VALUE = null;

        @Before
        public void setUp() {

            sut = new RequiredClass(INIT_VALUE);
        }

        @Test
        public void valueにはブランクが設定されている() {

            assertThat(sut.getValue(), is(""));
        }

        @Test
        public void validateを行うと必須チェックエラーになる() {

            final ValidateErrors actualErrors = sut.validate();

            assertThat(actualErrors.hasError(), is(true));

            final List<ValidateError> actualList = actualErrors.toList();

            final ValidateError expectedError = new ValidateError(ErrorInfo.Required, LABEL);
            assertThat(actualList.size(), is(1));
            assertThat(actualList.get(0), is(validatedBy(expectedError)));
        }
    }

    public static class 必須項目で初期値にブランクが設定されている場合 {
        
        private StubClass sut = null;

        private static final String INIT_VALUE = "";

        @Before
        public void setUp() {

            sut = new RequiredClass(INIT_VALUE);
        }

        @Test
        public void valueにはブランクが設定されている() {

            assertThat(sut.getValue(), is(""));
        }

        @Test
        public void validateを行うと必須チェックエラーになる() {

            final ValidateErrors actualErrors = sut.validate();

            assertThat(actualErrors.hasError(), is(true));

            final List<ValidateError> actualList = actualErrors.toList();

            final ValidateError expectedError = new ValidateError(ErrorInfo.Required, LABEL);
            assertThat(actualList.size(), is(1));
            assertThat(actualList.get(0), is(validatedBy(expectedError)));
        }
    }

    public static class 最大文字数が設定されている場合 {
        
        @Test
        public void 最大文字数以内の場合入力チェックエラーにならない() {

            final StubClass sut =  new MaxLengthClass("１２３４");

            final ValidateErrors actualErrors = sut.validate();

            assertThat(actualErrors.hasError(), is(false));
        }

        @Test
        public void 最大文字数を超えていて場合入力チェックエラーになる() {

            final StubClass sut =  new MaxLengthClass("１２３４５");

            final ValidateErrors actualErrors = sut.validate();

            assertThat(actualErrors.hasError(), is(true));

            final List<ValidateError> actualList = actualErrors.toList();

            final ValidateError expectedError = new ValidateError(ErrorInfo.MaxLengthOver, LABEL, "4");
            assertThat(actualList.size(), is(1));
            assertThat(actualList.get(0), is(validatedBy(expectedError)));
        }
    }
    
    public static class 許可文字列が指定されている場合 {

        @Test
        public void 許可文字列のみの場合入力チェックエラーにならない() {

            final StubClass sut = new ValidCharClass("123456789abcABC!#$%&'()");

            final ValidateErrors actualErrors = sut.validate();

            assertThat(actualErrors.hasError(), is(false));
        }

        @Test
        public void 許可文字列以外を含む場合は入力チェックエラーになる() {

            final StubClass sut = new ValidCharClass("1234あ5");

            final ValidateErrors actualErrors = sut.validate();

            assertThat(actualErrors.hasError(), is(true));

            final List<ValidateError> actualList = actualErrors.toList();

            final ValidateError expectedError = new ValidateError(ErrorInfo.InvalidCharacters, LABEL);
            assertThat(actualList.size(), is(1));
            assertThat(actualList.get(0), is(validatedBy(expectedError)));
        }
    }
}
