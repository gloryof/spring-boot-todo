package jp.glory.domain.todo.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.glory.domain.todo.value.Memo;
import jp.glory.domain.todo.value.Summary;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.domain.user.value.UserId;

class TodoTest {

    @DisplayName("すべての値が正常に設定されている場合")
    @Nested
    class AllValueIsValid {

        private Todo sut = null;

        @BeforeEach
        void setUp() {

            sut = new Todo(new TodoId(1000l), new UserId(1l), new Summary("概要テスト"), new Memo("新しいメモ"), false);
        }

        @DisplayName("versionでバージョンが設定されたエンティティが返る")
        @Test
        void testGetEntityVersion() {

            final long expectedVersion = 10l;

            assertEquals(1L, sut.getEntityVersion());

            sut.version(expectedVersion);

            assertEquals(expectedVersion, sut.getEntityVersion());
        }

        @DisplayName("isRegisteredにtrueが設定されている")
        @Test
        void testIsRegistered() {

            assertTrue(sut.isRegistered());
        }

        @DisplayName("isComplatedはfalse")
        @Test
        void testIsCompleted() {

            assertFalse(sut.isCompleted());
        }

        @DisplayName("markAsCompleteで実行済みになる")
        @Test
        void testMarkAsComplete() {

            sut.markAsComplete();

            assertTrue(sut.isCompleted());
        }

        @DisplayName("すでに実行済みの場合")
        @Nested
        class AlreadlyComplated {

            @BeforeEach
            void setUp() {

                sut.markAsComplete();
            }

            @DisplayName("unmarkFromCompleteで未実行になる")
            @Test
            void testUnmarkFromComplete() {

                sut.unmarkFromComplete();

                assertFalse(sut.isCompleted());
            }
        }
    }
}
