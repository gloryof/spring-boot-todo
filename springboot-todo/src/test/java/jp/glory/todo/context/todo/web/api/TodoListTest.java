package jp.glory.todo.context.todo.web.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jp.glory.todo.context.base.domain.error.ErrorInfo;
import jp.glory.todo.context.base.domain.error.ValidateError;
import jp.glory.todo.context.base.domain.error.ValidateErrors;
import jp.glory.todo.context.base.web.UserInfo;
import jp.glory.todo.context.base.web.exception.InvalidRequestException;
import jp.glory.todo.context.todo.domain.entity.Todo;
import jp.glory.todo.context.todo.domain.entity.Todos;
import jp.glory.todo.context.todo.domain.value.Memo;
import jp.glory.todo.context.todo.domain.value.Summary;
import jp.glory.todo.context.todo.domain.value.TodoId;
import jp.glory.todo.context.todo.usecase.SaveTodo;
import jp.glory.todo.context.todo.usecase.SearchTodo;
import jp.glory.todo.context.todo.web.api.TodoList;
import jp.glory.todo.context.todo.web.api.request.TodoCreateRequest;
import jp.glory.todo.context.todo.web.api.response.TodoCreateSuccessResponse;
import jp.glory.todo.context.todo.web.api.response.TodoListResponse;
import jp.glory.todo.context.todo.web.api.response.TodoStatistics;
import jp.glory.todo.context.user.domain.value.UserIdArgMatcher;
import jp.glory.todo.test.util.TestUserUtil;

class TodoListTest {


    private TodoList sut = null;

    private SearchTodo mockSearch = null;
    private SaveTodo mockSave = null;
    private UserInfo userInfo = null;

    @BeforeEach
    void setUp() {

        mockSearch = Mockito.mock(SearchTodo.class);
        mockSave = Mockito.mock(SaveTodo.class);

        userInfo = new UserInfo(TestUserUtil.createDefault());
    }

    @DisplayName("listのテスト")
    @Nested
    class TestList {

        private ResponseEntity<TodoListResponse> actual = null;
        private TodoListResponse actualResponse = null;

        @DisplayName("件数が0件の場合")
        @Nested
        class CountIs0 {

            @BeforeEach
            void setUp() {

                Mockito
                    .when(mockSearch.searchTodosByUser(UserIdArgMatcher.arg(userInfo.getUserId().getValue())))
                    .thenReturn(new Todos(new ArrayList<>()));

                sut = new TodoList(mockSearch, mockSave);

                actual = sut.list(userInfo);
                actualResponse = actual.getBody();
            }

            @DisplayName("ステータスはOK")
            @Test
            void assertStatus() {

                assertEquals(HttpStatus.OK, actual.getStatusCode());
            }

            @DisplayName("リストは空")
            @Test
            void asseertDetailsIsEmpty() {

                assertTrue(actualResponse.getDetails().isEmpty());
            }

            @DisplayName("統計の値のテスト")
            @Nested
            class Statistics {

                private TodoStatistics actualStatistics  = null;

                @BeforeEach
                void setUp() {

                    actualStatistics = actualResponse.getStatistics();
                }

                @DisplayName("トータルは0件")
                @Test
                void assertTotal() {

                    assertEquals(0, actualStatistics.getTotal());
                }

                @DisplayName("未実行は0件")
                @Test
                void assertUnexecuted() {

                    assertEquals(0, actualStatistics.getUnexecuted());
                }

                @DisplayName("実行済みは0件")
                @Test
                void assertExecuted() {

                    assertEquals(0, actualStatistics.getExecuted());
                }
            }
        }

        @DisplayName("実行済みのみの場合")
        @Nested
        class OnlyExecuted {

            @BeforeEach
            void setUp() {

                final List<Todo> todoList = LongStream.rangeClosed(1, 1)
                    .mapToObj(v -> new Todo(new TodoId(v), userInfo.getUserId(), Summary.empty(), Memo.empty(), true))
                    .collect(Collectors.toList());

                Mockito
                    .when(mockSearch.searchTodosByUser(UserIdArgMatcher.arg(userInfo.getUserId().getValue())))
                    .thenReturn(new Todos(todoList));

                sut = new TodoList(mockSearch, mockSave);

                actual = sut.list(userInfo);
                actualResponse = actual.getBody();
            }

            @DisplayName("ステータスはOK")
            @Test
            void assertStatus() {

                assertEquals(HttpStatus.OK, actual.getStatusCode());
            }

            @DisplayName("リストは1件")
            @Test
            void assertDetailCount() throws Exception {

                assertEquals(1, actualResponse.getDetails().size());
            }

            @DisplayName("統計の値のテスト")
            @Nested
            class Statistics {

                private TodoStatistics actualStatistics  = null;

                @BeforeEach
                void setUp() {

                    actualStatistics = actualResponse.getStatistics();
                }

                @DisplayName("トータルは1件")
                @Test
                void assertTotal() {

                    assertEquals(1, actualStatistics.getTotal());
                }

                @DisplayName("未実行は0件")
                @Test
                void assertUnexecuted() {

                    assertEquals(0, actualStatistics.getUnexecuted());
                }

                @DisplayName("実行済みは1件")
                @Test
                void assertExecuted() {

                    assertEquals(1, actualStatistics.getExecuted());
                }
            }
        }

        @DisplayName("未行済のみの場合")
        @Nested
        class OnlyUnexecuted {

            @BeforeEach
            void setUp() {

                final List<Todo> todoList = LongStream.rangeClosed(1, 1)
                    .mapToObj(v -> new Todo(new TodoId(v), userInfo.getUserId(), Summary.empty(), Memo.empty(), false))
                    .collect(Collectors.toList());

                Mockito
                    .when(mockSearch.searchTodosByUser(UserIdArgMatcher.arg(userInfo.getUserId().getValue())))
                    .thenReturn(new Todos(todoList));

                sut = new TodoList(mockSearch, mockSave);

                actual = sut.list(userInfo);
                actualResponse = actual.getBody();
            }

            @DisplayName("ステータスはOK")
            @Test
            void assertStatus() {

                assertEquals(HttpStatus.OK, actual.getStatusCode());
            }

            @DisplayName("リストは1件")
            @Test
            void assertDetailCount() throws Exception {

                assertEquals(1, actualResponse.getDetails().size());
            }

            @DisplayName("統計の値のテスト")
            @Nested
            class Statistics {

                private TodoStatistics actualStatistics  = null;

                @BeforeEach
                void setUp() {

                    actualStatistics = actualResponse.getStatistics();
                }

                @DisplayName("トータルは1件")
                @Test
                void assertTotal() {

                    assertEquals(1, actualStatistics.getTotal());
                }

                @DisplayName("未実行は1件")
                @Test
                void assertUnexecuted() {

                    assertEquals(1, actualStatistics.getUnexecuted());
                }

                @DisplayName("実行済みは0件")
                @Test
                void assertExecuted() {

                    assertEquals(0, actualStatistics.getExecuted());
                }
            }
        }

        @DisplayName("実行済み:3件 未実行:2件")
        @Nested
        class MultiPattern {

            @BeforeEach
            void setUp() {

                final List<Todo> executedList = LongStream.rangeClosed(1, 3)
                        .mapToObj(v -> new Todo(new TodoId(v), userInfo.getUserId(), Summary.empty(), Memo.empty(), true))
                        .collect(Collectors.toList());

                final List<Todo> unexcutedList = LongStream.rangeClosed(4, 5)
                        .mapToObj(v -> new Todo(new TodoId(v), userInfo.getUserId(), Summary.empty(), Memo.empty(), false))
                        .collect(Collectors.toList());

                final List<Todo> todoList = new ArrayList<>(executedList);
                todoList.addAll(unexcutedList);

                Mockito
                    .when(mockSearch.searchTodosByUser(UserIdArgMatcher.arg(userInfo.getUserId().getValue())))
                    .thenReturn(new Todos(todoList));

                sut = new TodoList(mockSearch, mockSave);

                actual = sut.list(userInfo);
                actualResponse = actual.getBody();
            }


            @DisplayName("ステータスはOK")
            @Test
            void assertStatus() {

                assertEquals(HttpStatus.OK, actual.getStatusCode());
            }

            @DisplayName("リストは5件")
            @Test
            void assertDetailCount() throws Exception {

                assertEquals(5, actualResponse.getDetails().size());
            }


            @DisplayName("統計の値のテスト")
            @Nested
            class Statistics {

                private TodoStatistics actualStatistics  = null;

                @BeforeEach
                void setUp() {

                    actualStatistics = actualResponse.getStatistics();
                }

                @DisplayName("トータルは5件")
                @Test
                void assertTotal() {

                    assertEquals(5, actualStatistics.getTotal());
                }

                @DisplayName("未実行は2件")
                @Test
                void assertUnexecuted() {

                    assertEquals(2, actualStatistics.getUnexecuted());
                }

                @DisplayName("実行済みは3件")
                @Test
                void assertExecuted() {

                    assertEquals(3, actualStatistics.getExecuted());
                }
            }
        }
    }

    @DisplayName("createTodoのテスト")
    @Nested
    class CreateTodo {

        private SaveTodo.Result mockUseCaseResult;

        final TodoId expectedTodoId = new TodoId(1000l);

        private ResponseEntity<TodoCreateSuccessResponse> actual = null;

        @BeforeEach
        void setUp() {

            mockUseCaseResult = mock(SaveTodo.Result.class);
        }

        @DisplayName("入力内容に不備がない場合")
        @Nested
        class ValueIsValid {

            @BeforeEach
            void setUp() {

                Mockito.when(mockUseCaseResult.getErrors()).thenReturn(new ValidateErrors());
                Mockito.when(mockUseCaseResult.getSavedTodoId()).thenReturn(expectedTodoId);
                Mockito.when(mockSave.save(Mockito.any())).thenReturn(mockUseCaseResult);

                sut = new TodoList(mockSearch, mockSave);

                userInfo = new UserInfo(TestUserUtil.createDefault());
                actual = sut.save(new TodoCreateRequest(), userInfo);
            }


            @DisplayName("ステータスはCREATED")
            @Test
            void assertStatusCode() {

                assertEquals(HttpStatus.CREATED, actual.getStatusCode());
            }
        }

        @DisplayName("入力内容に不備がある場合")
        @Nested
        class ValueIsInvalid {

            private ValidateErrors expectedErrors = null;

            @BeforeEach
            void setUp() {


                expectedErrors = new ValidateErrors();
                expectedErrors.add(new ValidateError(ErrorInfo.Required, Summary.LABEL));
                expectedErrors.add(new ValidateError(ErrorInfo.MaxLengthOver, Memo.LABEL, 1000));

                Mockito.when(mockUseCaseResult.getErrors()).thenReturn(expectedErrors);
                Mockito.when(mockUseCaseResult.getSavedTodoId()).thenReturn(expectedTodoId);
                Mockito.when(mockSave.save(Mockito.any())).thenReturn(mockUseCaseResult);

                userInfo = new UserInfo(TestUserUtil.createDefault());
                sut = new TodoList(mockSearch, mockSave);
            }

            @DisplayName("InvalidRequestExceptionがスローされる")
            @Test
            void asseertThrowException() {

                final InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                        () -> sut.save(new TodoCreateRequest(), userInfo));
                final ValidateErrors actualErrors = exception.getErrors();

                for (int i = 0; i < actualErrors.toList().size(); i++) {

                    final String actualMessage = actualErrors.toList().get(i).getMessage();
                    final String expectedMessage = expectedErrors.toList().get(i).getMessage();
                    assertEquals(expectedMessage, actualMessage);
                }
            }

        }
    }

}
