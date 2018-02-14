package jp.glory.todo.context.todo.usecase;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
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
import jp.glory.todo.context.todo.domain.repository.TodoRepository;
import jp.glory.todo.context.todo.domain.value.Summary;
import jp.glory.todo.context.todo.domain.value.TodoId;
import jp.glory.todo.context.user.domain.value.UserId;

class SearchTodosTest {

    private SearchTodo sut = null;
    private TodoRepository mock = null;

    @BeforeEach
    void setUp() {

        mock = mock(TodoRepository.class);
        sut = new SearchTodo(mock);
    }

    @DisplayName("searchByIdのテスト")
    @Nested
    class SearchById {

        @DisplayName("指定したIDに紐づくTodoが返る")
        @Test
        void testMatchId() {

            final TodoId expectedTodoId = new TodoId(20l);

            final Todo expected = new Todo(expectedTodoId, new UserId(1234l), new Summary("test"));

            when(mock.findBy(any(TodoId.class))).thenReturn(Optional.of(expected));


            final Optional<Todo> actual = sut.searchById(expectedTodoId);

            assertTrue(actual.isPresent());
            assertTrue(actual.get().getId().isSame(expectedTodoId));
        }

        @DisplayName("IDに紐づくTodoがない場合はOptionaは空")
        @Test
        void testNotMatchId() {

            when(mock.findBy(any(TodoId.class))).thenReturn(Optional.empty());

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


        private final UserId expectedUserId = new UserId(3l);

        @DisplayName("指定したユーザIDのTODOが10件存在する場合")
        @Nested
        class TodoIsExisits {

            @BeforeEach
            void setUp() {

                List<Todo> todos = LongStream.rangeClosed(1, 10)
                                        .mapToObj(v -> new Todo(new TodoId(v), expectedUserId, Summary.empty()))
                                        .collect(Collectors.toList());
                when(mock.findTodosBy(any(UserId.class))).thenReturn(new Todos(todos));

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

                when(mock.findTodosBy(any(UserId.class))).thenReturn(new Todos(Collections.emptyList()));

                actual = sut.searchTodosByUser(expectedUserId);

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
