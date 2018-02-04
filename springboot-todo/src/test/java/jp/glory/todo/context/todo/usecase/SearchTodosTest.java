package jp.glory.todo.context.todo.usecase;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.glory.todo.context.todo.domain.entity.Todo;
import jp.glory.todo.context.todo.domain.entity.Todos;
import jp.glory.todo.context.todo.domain.repository.TodoRepositoryMock;
import jp.glory.todo.context.todo.domain.value.Memo;
import jp.glory.todo.context.todo.domain.value.Summary;
import jp.glory.todo.context.todo.domain.value.TodoId;
import jp.glory.todo.context.todo.usecase.SearchTodo;
import jp.glory.todo.context.user.domain.value.UserId;

class SearchTodosTest {

    private SearchTodo sut = null;
    private TodoRepositoryMock mock = null;

    @BeforeEach
    void setUp() {

        mock = new TodoRepositoryMock();
    }

    @DisplayName("searchByIdのテスト")
    @Nested
    class SearchById {

        @BeforeEach
        void setUp() {


            LongStream.rangeClosed(1, 100).mapToObj(v -> {

                return new Todo(new TodoId(v), UserId.notNumberingValue(), Summary.empty(), Memo.empty(), true);
            }).forEach(mock::addTestData);
            sut = new SearchTodo(mock);
        }

        @DisplayName("指定したIDに紐づくTodoが返る")
        @Test
        void testMatchId() {

            final TodoId expectedTodoId = new TodoId(20l);

            final Optional<Todo> actual = sut.searchById(expectedTodoId);

            assertTrue(actual.isPresent());
            assertTrue(actual.get().getId().isSame(expectedTodoId));
        }

        @DisplayName("IDに紐づくTodoがない場合はOptionaは空")
        @Test
        void testNotMatchId() {

            final TodoId expectedTodoId = new TodoId(120l);

            final Optional<Todo> actual = sut.searchById(expectedTodoId);

            assertFalse(actual.isPresent());
        }
    }

    @DisplayName("searchByUserのテスト")
    @Nested
    class SearchByUser {

        private Todos actual = null;
        private Todos.Statistics actualStatistics = null;
        private List<Todo> actualList = null;

        @BeforeEach
        void setUp() {

            LongStream.rangeClosed(1, 100).mapToObj(v -> {
                final UserId userId = new UserId(v % 10);

                return new Todo(new TodoId(v), userId, Summary.empty(), Memo.empty(), true);
            }).forEach(mock::addTestData);
            sut = new SearchTodo(mock);
        }

        @DisplayName("指定したユーザIDのTODOが10件存在する場合")
        @Nested
        class TodoIsExisits {

            private final UserId expectedUserId = new UserId(3l);

            @BeforeEach
            void setUp() {

                actual = sut.searchTodosByUser(expectedUserId);

                actualStatistics  = actual.getStatistics();
                actualList = actual.asList();
            }

            @DisplayName("トータル件数が10件")
            @Test
            void assertTotal() {

                assertEquals(10, actualStatistics.getTotal());
            }

            @DisplayName("リストのサイズが10件")
            @Test
            void assertSize() {

                assertEquals(10, actualList.size());
            }

            @DisplayName("全てのTODOのユーザID一致する")
            void 指定したユーザIDのTodoリストが返る() {

                final List<Todo> actualUserIdFiltered = actualList.stream()
                        .filter(v -> v.getUserId().isSame(expectedUserId)).collect(Collectors.toList());
                assertEquals(10, actualUserIdFiltered.size());
            }
        }

        @DisplayName("指定したユーザIDのTODOが存在しない場合")
        @Nested
        class TodoIsNoExisits {


            @BeforeEach
            void setUp() {

                actual = sut.searchTodosByUser(new UserId(10000l));

                actualStatistics  = actual.getStatistics();
                actualList = actual.asList();
            }


            @DisplayName("トータル件数が0件")
            @Test
            void assertTotal() {

                assertEquals(0, actualStatistics.getTotal());
            }

            @DisplayName("リストのサイズが0件")
            @Test
            void assertSize() {

                assertEquals(0, actualList.size());
            }
        }
    }
}
