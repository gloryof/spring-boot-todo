package jp.glory.domain.common.annotation.param;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

class ValidCharcterTypeTest {

    @DisplayName("OnlySingleByteCharsのテスト")
    @Nested
    class OnlySingleByteChars {

        private final ValidCharcterType sut = ValidCharcterType.OnlySingleByteChars;

        @DisplayName("半角文字のみの場合isMatcheでtrueが返却される")
        @ParameterizedTest
        @ArgumentsSource(AllowerCharactes.class)
        void allowedIsReturnTrue (String value) {

            assertTrue(sut.isMatch(value));
        }

        @DisplayName("半角文字以外の場合isMatcheでfalseが返却される")
        @ParameterizedTest
        @ArgumentsSource(NotAllowerCharactes.class)
        void notAllowedIsReturnFalse(String value) {

            assertFalse(sut.isMatch(value));
        }
    }

    static class AllowerCharactes implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {

            return Stream.of("0123456789", "abcdefghijklmnopqrstuvwxyz", "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
                    "!\"#$%&'()=~|+*}<>?_,./\\").map(Arguments::of);
        }
    }

    static class NotAllowerCharactes implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {

            return Stream.of("１").map(Arguments::of);
        }
    }
}
