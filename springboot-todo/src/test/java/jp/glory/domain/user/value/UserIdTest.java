package jp.glory.domain.user.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class UserIdTest {

    @DisplayName("notNumberingValueのテスト")
    @Nested
    class TestNotNuberingValue {

        @DisplayName("値が0で未採番の値が返却される")
        @Test
        void assertValue () {

            final UserId actual = UserId.notNumberingValue();

            assertEquals((Long) 0L, actual.getValue());
            assertFalse(actual.isSetValue());
        }
    }
}