package jp.glory.domain.user.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import jp.glory.domain.common.error.ErrorInfo;
import jp.glory.domain.common.error.ValidateError;
import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.test.validate.ValidateAssert;

class PasswordTest {

    private Password sut = null;
    private ValidateErrors actualErrors = null;

    @DisplayName("emptyのテスト")
    @Nested
    class TestEmpty {

        @DisplayName("ブランクが返る")
        @Test
        void returnBlank() {

            sut = Password.empty();

            assertEquals("", sut.getValue());
        }
    }

    @DisplayName("値が設定されている場合")
    @Nested
    class WhenValueIsSet {

        private final String ENCRYPTED_VALUE = "19CB2A070DDBE8157E17C5DDA0EA38E8AA16FAE1725C1F7AC22747D870368579";

        @BeforeEach
        void setUp() {

            sut = new Password(ENCRYPTED_VALUE);
        }

        @DisplayName("isMatchのテスト")
        @Nested
        class TestIsMatch {

            @DisplayName("パスワードが一致している場合はtrue")
            @Test
            void ifMatchPassword () {

                final Password comparePassword = new Password(ENCRYPTED_VALUE);

                assertTrue(sut.isMatch(comparePassword));
            }

            @DisplayName("パスワードが一致していない場合はfalse")
            @ArgumentsSource(DifferentValueProvider.class)
            @ParameterizedTest(name = "[{index}] Password({0})")
            void ifNotMatchPassword (final String label, final Password comparePassword) {

                assertFalse(sut.isMatch(comparePassword));
            }
        }

        @DisplayName("validateのテスト")
        @Nested
        class TestValidate {

            @BeforeEach
            void setUp() {

                actualErrors = sut.validate();
            }

            @DisplayName("validateを行っても入力チェックエラーにならない")
            @Test
            void notError() {

                assertFalse(actualErrors.hasError());
            }
        }
    }

    @DisplayName("空文字が設定されている場合")
    @Nested
    class WhenBlankIsSet {

        @BeforeEach
        void setUp() {

            sut = new Password("");
        }

        @DisplayName("isMatchのテスト")
        @Nested
        class TestIsMatch {

            @DisplayName("パスワードが一致している場合でもfasle")
            @Test
            void returnFalse () {

                final Password comparePassword = new Password("");

                assertFalse(sut.isMatch(comparePassword));
            }
        }

        @DisplayName("validateのテスト")
        @Nested
        class TestValidate {

            @BeforeEach
            void setUp() {

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

                final ValidateError expectedError = new ValidateError(ErrorInfo.Required, Password.LABEL);

                final ValidateAssert validate = new ValidateAssert(expectedError, actualErrors);
                validate.assertAll();
            }
        }
    }

    @DisplayName("Nullが設定されている場合")
    @Nested
    class WhenNullIsSet {

        @BeforeEach
        void setUp() {

            sut = new Password(null);
        }

        @DisplayName("isMatchのテスト")
        @Nested
        class TestIsMatch {

            @DisplayName("パスワードが一致している場合でもfasle")
            @Test
            void returnFalse () {

                final Password comparePassword = new Password("");

                assertFalse(sut.isMatch(comparePassword));
            }
        }

        @DisplayName("validateのテスト")
        @Nested
        class TestValidate {

            @BeforeEach
            void setUp() {

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

                final ValidateError expectedError = new ValidateError(ErrorInfo.Required, Password.LABEL);

                final ValidateAssert validate = new ValidateAssert(expectedError, actualErrors);
                validate.assertAll();
            }
        }
    }

    @DisplayName("値に使用できない文字のみ設定されている場合")
    @Nested
    class WhenInvalidCharacters {

        @BeforeEach
        void setUp() {

            sut = new Password("あ");
        }

        @DisplayName("isMatchのテスト")
        @Nested
        class TestIsMatch {

            @DisplayName("パスワードが一致している場合はtrue")
            @Test
            void ifMatchPassword () {

                final Password comparePassword = new Password("あ");

                assertTrue(sut.isMatch(comparePassword));
            }

            @DisplayName("パスワードが一致していない場合はfalse")
            @ArgumentsSource(DifferentValueProvider.class)
            @ParameterizedTest(name = "[{index}] Password({0})")
            void ifNotMatchPassword (final String label, final Password comparePassword) {

                assertFalse(sut.isMatch(comparePassword));
            }
        }


        @DisplayName("validateのテスト")
        @Nested
        class TestValidate {

            @BeforeEach
            void setUp() {

                actualErrors = sut.validate();
            }

            @DisplayName("hasErrorはtrue")
            @Test
            void hasError() {

                assertTrue(actualErrors.hasError());
            }

            @DisplayName("文字種エラーになる")
            @Test
            void assertErrors() {

                final ValidateError expectedError = new ValidateError(ErrorInfo.InvalidCharacters, Password.LABEL);

                final ValidateAssert validate = new ValidateAssert(expectedError, actualErrors);
                validate.assertAll();
            }
        }
    }

    static class DifferentValueProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {

            

            return Stream.of(new Password("test"), new Password("test2"), null).map(this::createArguments);
        }

        private Arguments createArguments(final Password password) {

            if (password == null) {

                return Arguments.of(null, null);
            }

            return Arguments.of(password.getValue(), password);
        }
    }
}