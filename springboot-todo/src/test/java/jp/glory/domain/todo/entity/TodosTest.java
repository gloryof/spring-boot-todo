package jp.glory.domain.todo.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.glory.domain.todo.entity.Todos.Statistics;
import jp.glory.domain.todo.value.Memo;
import jp.glory.domain.todo.value.Summary;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.domain.user.value.UserId;

class TodosTest {

    private Todos sut = null;

    @DisplayName("コンストラクタにNullが渡された場合")
    @Nested
    class WhenConstructerParamNull {

        @BeforeEach
        void setUp() {

            sut = new Todos(null);
        }

        @DisplayName("未実行TODOはない")
        @Test
        void testHasUnexecuted() {

            assertFalse(sut.hasUnexecuted());
        }

        @DisplayName("asListで空のListが返される")
        @Test
        void testIsEmpty() {

            final List<Todo> actual = sut.asList();

            assertTrue(actual.isEmpty());
        }

        @DisplayName("Statisticsのに関するテスト")
        @Nested
        class TestStatistics {

            private Statistics actualStatistics = null;

            @BeforeEach
            void setUp() {

                actualStatistics = sut.getStatistics();
            }

            @DisplayName("トータル件数は0件")
            @Test
            void testGetTotal() {

                assertEquals(0, actualStatistics.getTotal());
            }

            @DisplayName("実行済みは0件")
            @Test
            void testGetExecuted() {

                assertEquals(0, actualStatistics.getExecuted());
            }

            @DisplayName("未実行は0件")
            @Test
            void testGetUnexecuted() {

                assertEquals(0, actualStatistics.getUnexecuted());
            }
        }
    }

    @DisplayName("Todoが0件の場合")
    @Nested
    class WhenTodoCountIsZero {

        private Todos sut = null;

        @BeforeEach
        void setUp() {

            sut = new Todos(new ArrayList<>());
        }

        @DisplayName("未実行TODOはない")
        @Test
        void testHasUnexecuted() {

            assertFalse(sut.hasUnexecuted());
        }

        @DisplayName("asListで空のListが返される")
        @Test
        void testAsList() {

            final List<Todo> actual = sut.asList();

            assertTrue(actual.isEmpty());
        }

        @DisplayName("Statisticsのに関するテスト")
        @Nested
        class TestStatistics {

            private Statistics actualStatistics = null;

            @BeforeEach
            void setUp() {

                actualStatistics = sut.getStatistics();
            }

            @DisplayName("トータル件数は0件")
            @Test
            void testGetTotal() {

                assertEquals(0, actualStatistics.getTotal());
            }

            @DisplayName("実行済みは0件")
            @Test
            void testGetExecuted() {

                assertEquals(0, actualStatistics.getExecuted());
            }

            @DisplayName("未実行は0件")
            @Test
            void testGetUnexecuted() {

                final Statistics actualStatistics = sut.getStatistics();
                assertEquals(0, actualStatistics.getUnexecuted());
            }
        }
    }

    @DisplayName("未実行Todoが1件の場合")
    @Nested
    class WhenTodoUnexecutedCountIsOne {

        private Todos sut = null;

        @BeforeEach
        void setUp() {

            final List<Todo> list = new ArrayList<>();
            list.add(new Todo(new TodoId(1l), new UserId(10l), Summary.empty(), Memo.empty(), false));

            sut = new Todos(list);
        }

        @DisplayName("未実行TODOはある")
        @Test
        void testHasUnexecuted() {

            assertTrue(sut.hasUnexecuted());
        }

        @DisplayName("asListで1件のListが返される")
        @Test
        void testAsList() {

            final List<Todo> actual = sut.asList();

            assertEquals(1, actual.size());
        }

        @DisplayName("Statisticsのに関するテスト")
        @Nested
        class TestStatistics {

            private Statistics actualStatistics = null;

            @BeforeEach
            void setUp() {

                actualStatistics = sut.getStatistics();
            }

            @DisplayName("トータル件数は1件")
            @Test
            void testGetTotal() {

                assertEquals(1, actualStatistics.getTotal());
            }

            @DisplayName("実行済みは0件")
            @Test
            void testGetExecuted() {

                final Statistics actualStatistics = sut.getStatistics();
                assertEquals(0, actualStatistics.getExecuted());
            }

            @DisplayName("未実行は1件")
            @Test
            void testGetUnexecuted() {

                final Statistics actualStatistics = sut.getStatistics();
                assertEquals(1, actualStatistics.getUnexecuted());
            }
        }
    }

    @DisplayName("実行済Todoが1件の場合")
    @Nested
    class WhenTodoExecutedCountIsOne {

        private Todos sut = null;

        @BeforeEach
        void setUp() {

            final List<Todo> list = new ArrayList<>();
            list.add(new Todo(new TodoId(1l), new UserId(10l), Summary.empty(), Memo.empty(), true));

            sut = new Todos(list);
        }

        @DisplayName("未実行TODOはない")
        @Test
        void testHasUnexecuted() {

            assertFalse(sut.hasUnexecuted());
        }

        @DisplayName("asListで1件のListが返される")
        @Test
        void testAsList() {

            final List<Todo> actual = sut.asList();

            assertEquals(1, actual.size());
        }

        @DisplayName("Statisticsのに関するテスト")
        @Nested
        class TestStatistics {

            private Statistics actualStatistics = null;

            @BeforeEach
            void setUp() {

                actualStatistics = sut.getStatistics();
            }

            @DisplayName("トータル件数は1件")
            @Test
            void testGetTotal() {

                assertEquals(1, actualStatistics.getTotal());
            }

            @DisplayName("実行済みは1件")
            @Test
            void testGetExecuted() {

                assertEquals(1, actualStatistics.getExecuted());
            }

            @DisplayName("未実行は0件")
            @Test
            void testGetUnexecuted() {

                assertEquals(0, actualStatistics.getUnexecuted());
            }
        }
    }

    @DisplayName("(未実行 : 3件) + (実行済み : 2件) = (合計 : 5件）")
    @Nested
    class MultipleCase {

        private Todos sut = null;

        @BeforeEach
        void setUp() {

            final List<Todo> list = new ArrayList<>();

            LongStream.rangeClosed(1, 3).forEach(
                    v -> list.add(new Todo(new TodoId(v), new UserId(v), Summary.empty(), Memo.empty(), false)));

            LongStream.rangeClosed(1, 2).forEach(
                    v -> list.add(new Todo(new TodoId(v), new UserId(v), Summary.empty(), Memo.empty(), true)));

            sut = new Todos(list);
        }

        @DisplayName("未実行TODOはある")
        @Test
        void testHasUnexecuted() {

            assertTrue(sut.hasUnexecuted());
        }

        @DisplayName("asListで5件のListが返される")
        @Test
        void testAsList() {

            final List<Todo> actual = sut.asList();

            assertEquals(5, actual.size());
        }

        @DisplayName("Statisticsのに関するテスト")
        @Nested
        class TestStatistics {

            @DisplayName("トータル件数は実行済_未実行を合計した数")
            @Test
            void testGetTotal() {

                final Statistics actualStatistics = sut.getStatistics();
                assertEquals(5, actualStatistics.getTotal());
            }

            @DisplayName("実行済みは2件")
            @Test
            void testGetExecuted() {

                final Statistics actualStatistics = sut.getStatistics();
                assertEquals(2, actualStatistics.getExecuted());
            }

            @DisplayName("未実行は3件")
            @Test
            void testGetUnexecuted() {

                final Statistics actualStatistics = sut.getStatistics();
                assertEquals(3, actualStatistics.getUnexecuted());
            }
        }
    }
}
