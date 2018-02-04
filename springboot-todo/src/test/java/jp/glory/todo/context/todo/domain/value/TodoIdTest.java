package jp.glory.todo.context.todo.domain.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.glory.todo.context.todo.domain.value.TodoId;

class TodoIdTest {

    @DisplayName("notNumberingValueのテスト")
    @Nested
    class testNotNumberingValue {

        @Test
        public void 値が0で未採番の値が返却される() {

            final TodoId actual = TodoId.notNumberingValue();

            assertEquals((Long) 0L, actual.getValue());
            assertFalse(actual.isSetValue());
        }
    }
}
