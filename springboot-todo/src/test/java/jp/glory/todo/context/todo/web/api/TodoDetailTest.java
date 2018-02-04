package jp.glory.todo.context.todo.web.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jp.glory.todo.context.base.domain.error.ErrorInfo;
import jp.glory.todo.context.base.domain.error.ValidateError;
import jp.glory.todo.context.base.domain.error.ValidateErrors;
import jp.glory.todo.context.base.web.UserInfo;
import jp.glory.todo.context.todo.domain.entity.Todo;
import jp.glory.todo.context.todo.domain.value.Memo;
import jp.glory.todo.context.todo.domain.value.Summary;
import jp.glory.todo.context.todo.domain.value.TodoId;
import jp.glory.todo.context.todo.domain.value.TodoIdArgMatcher;
import jp.glory.todo.context.todo.usecase.SaveTodo;
import jp.glory.todo.context.todo.usecase.SearchTodo;
import jp.glory.todo.context.todo.usecase.SaveTodo.Result;
import jp.glory.todo.context.todo.web.api.TodoDetail;
import jp.glory.todo.context.todo.web.api.request.TodoDetailSaveRequest;
import jp.glory.todo.context.todo.web.api.response.TodoDetailResponse;
import jp.glory.todo.context.user.domain.entity.User;
import jp.glory.todo.context.user.domain.value.UserId;
import jp.glory.todo.context.user.web.exception.InvalidRequestException;
import jp.glory.todo.test.util.TestUserUtil;

class TodoDetailTest {

    private final long TARGET_ID = 100;

    private TodoDetail sut = null;

    private SearchTodo mockSearch = null;
    private SaveTodo mockSave = null;

    private Todo expectedTodo = null;

    @BeforeEach
    void setUp() {

        mockSearch = Mockito.mock(SearchTodo.class);
        mockSave = Mockito.mock(SaveTodo.class);
    }

    @DisplayName("viewのテスト")
    @Nested
    class View {

        private ResponseEntity<TodoDetailResponse> actual = null;

        @DisplayName("対象のデータが存在する場合")
        @Nested
        class IfExists {

            @BeforeEach
            void setUp() {

                expectedTodo = new Todo(new TodoId(TARGET_ID), new UserId(200l), new Summary("タイトルー"), new Memo("メモ"), true);
                expectedTodo.version(20l);

                Mockito
                    .when(mockSearch.searchById(TodoIdArgMatcher.arg(TARGET_ID)))
                    .thenReturn(Optional.of(expectedTodo));

                sut = new TodoDetail(mockSearch, mockSave);

                actual = sut.view(TARGET_ID);
            }

            @DisplayName("ステータスはOK")
            @Test
            void assertStatus() {

                assertEquals(HttpStatus.OK, actual.getStatusCode());
            }

            @DisplayName("Todoの内容が返る")
            @Test
            void assertTodoDetails() throws Exception {

                final TodoDetailResponse actualResponse = actual.getBody();

                assertEquals(expectedTodo.getId().getValue().longValue(), actualResponse.getId());
                assertEquals(expectedTodo.getSummary().getValue(), actualResponse.getSummary());
                assertEquals(expectedTodo.getMemo().getValue(), actualResponse.getMemo());
                assertEquals(expectedTodo.isCompleted(), actualResponse.isCompleted());
                assertEquals(expectedTodo.getEntityVersion(), actualResponse.getVersion());
            }
        }

        @DisplayName("対象のデータが存在しない場合")
        @Nested
        class IfNotExists {

            @BeforeEach
            void setUp() {

                Mockito
                    .when(mockSearch.searchById(TodoIdArgMatcher.arg(TARGET_ID)))
                    .thenReturn(Optional.empty());

                sut = new TodoDetail(mockSearch, mockSave);

                actual = sut.view(TARGET_ID);
            }

            @DisplayName("ステータスはNotFound")
            @Test
            void assertStatusCode() throws Exception {

                assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
            }
        }
    }

    @DisplayName("saveのテスト")
    @Nested
    class Save {


        private TodoDetailSaveRequest request = null;
        private SaveTodo.Result mockUseCaseResult = null;
        private final User user = TestUserUtil.createDefault();

        private ResponseEntity<Object> actual = null;
        private Todo actualTodo = null;

        @BeforeEach
        void setUp () {

            mockUseCaseResult = Mockito.mock(SaveTodo.Result.class);
        }

        @DisplayName("対象のデータが存在している場合")
        @Nested
        class IfExisits {

            @BeforeEach
            void setUp() {

                expectedTodo = new Todo(new TodoId(TARGET_ID), user.getUserId(), new Summary("タイトルー"), new Memo("メモ"),
                        true);
                expectedTodo.version(10l);

                Mockito
                    .when(mockSearch.searchById(TodoIdArgMatcher.arg(TARGET_ID)))
                    .thenReturn(Optional.of(expectedTodo));

                Mockito
                    .when(mockSearch.searchById(TodoIdArgMatcher.arg(TARGET_ID)))
                    .thenReturn(Optional.of(expectedTodo));
            }

            @DisplayName("入力内容に不備がない場合")
            @Nested
            class ValueIsValid {


                @BeforeEach
                void setUp() {

                    Mockito.when(mockUseCaseResult.getErrors()).thenReturn(new ValidateErrors());
                    Mockito.when(mockSave.save(Mockito.any())).then(new Answer<SaveTodo.Result>() {

                        @Override
                        public Result answer(InvocationOnMock invocation) throws Throwable {

                            actualTodo = invocation.getArgumentAt(0, Todo.class);
                            return mockUseCaseResult;
                        }
                    });

                    request = new TodoDetailSaveRequest();
                    request.setSummary(expectedTodo.getSummary().getValue());
                    request.setMemo(expectedTodo.getMemo().getValue());
                    request.setCompleted(expectedTodo.isCompleted());
                    request.setVersion(expectedTodo.getEntityVersion());

                    sut = new TodoDetail(mockSearch, mockSave);
                    actual = sut.save(TARGET_ID, request, new UserInfo(user));
                }

                @DisplayName("NoContetが返る")
                @Test
                void assertStatusCode() throws Exception {

                    assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
                }

                @Test
                void 設定された内容が保存される() throws Exception {

                    assertEquals(expectedTodo.getId().getValue(), actualTodo.getId().getValue());
                    assertEquals(expectedTodo.getSummary().getValue(), actualTodo.getSummary().getValue());
                    assertEquals(expectedTodo.getMemo().getValue(), actualTodo.getMemo().getValue());
                    assertEquals(expectedTodo.isCompleted(), actualTodo.isCompleted());
                    assertEquals(expectedTodo.getEntityVersion(), actualTodo.getEntityVersion());
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
                    Mockito.when(mockSave.save(Mockito.any())).thenReturn(mockUseCaseResult);

                    sut = new TodoDetail(mockSearch, mockSave);
                }

                @DisplayName("InvalidRequestExceptionがスローされる")
                @Test
                void assertThrowException() {

                    final InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                            () -> sut.save(TARGET_ID, new TodoDetailSaveRequest(), new UserInfo(user)));

                    final ValidateErrors actualErrors = exception.getErrors();

                    for (int i = 0; i < actualErrors.toList().size(); i++) {

                        final String actualMessage = actualErrors.toList().get(i).getMessage();
                        final String expectedMessage = expectedErrors.toList().get(i).getMessage();
                        assertEquals(expectedMessage, actualMessage);
                    }
                }
            }

        }


        @DisplayName("対象のデータが存在しない場合")
        @Nested
        class IfNotExists {

            private ResponseEntity<Object> actual = null;

            @BeforeEach
            void setUp() {

                Mockito
                    .when(mockSearch.searchById(TodoIdArgMatcher.arg(TARGET_ID)))
                    .thenReturn(Optional.empty());

                sut = new TodoDetail(mockSearch, mockSave);

                actual = sut.save(TARGET_ID, new TodoDetailSaveRequest(), new UserInfo(TestUserUtil.createDefault()));
            }

            @DisplayName("ステータスはNOT_FOUND")
            @Test
            void assertStatus() {

                assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
            }
        }
    }
}
