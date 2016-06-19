package jp.glory.web.api.todo;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jp.glory.domain.common.error.ErrorInfo;
import jp.glory.domain.common.error.ValidateError;
import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.domain.todo.entity.Todo;
import jp.glory.domain.todo.value.Memo;
import jp.glory.domain.todo.value.Summary;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.domain.todo.value.TodoIdArgMatcher;
import jp.glory.domain.user.entity.User;
import jp.glory.domain.user.value.UserId;
import jp.glory.framework.web.exception.InvalidRequestException;
import jp.glory.test.util.TestUserUtil;
import jp.glory.usecase.todo.SaveTodo;
import jp.glory.usecase.todo.SaveTodo.Result;
import jp.glory.usecase.todo.SearchTodo;
import jp.glory.web.api.todo.request.TodoDetailSaveRequest;
import jp.glory.web.api.todo.response.TodoDetailResponse;
import jp.glory.web.session.UserInfo;

@RunWith(Enclosed.class)
public class TodoDetailTest {

    private static final long TARGET_ID = 100;

    @RunWith(Enclosed.class)
    public static class viewのテスト {

        public static class 対象のデータが存在する場合 {

            private TodoDetail sut = null;

            private SearchTodo mockSearch = null;
            private SaveTodo mockSave = null;

            private Todo expectedTodo = null;

            private ResponseEntity<TodoDetailResponse> actual = null;

            @Before
            public void setUp() {

                mockSearch = Mockito.mock(SearchTodo.class);
                mockSave = Mockito.mock(SaveTodo.class);

                expectedTodo = new Todo(new TodoId(TARGET_ID), new UserId(200l), new Summary("タイトルー"), new Memo("メモ"), true);
                expectedTodo.version(20l);

                Mockito
                    .when(mockSearch.searchById(TodoIdArgMatcher.arg(TARGET_ID)))
                    .thenReturn(Optional.of(expectedTodo));

                sut = new TodoDetail(mockSearch, mockSave);

                actual = sut.view(TARGET_ID);
            }

            @Test
            public void ステータスはOK() {

                assertThat(actual.getStatusCode(), is(HttpStatus.OK));
            }

            @Test
            public void Todoの内容が返る() throws Exception {

                final TodoDetailResponse actualResponse = actual.getBody();

                assertThat(actualResponse.getId(), is(expectedTodo.getId().getValue()));
                assertThat(actualResponse.getSummary(), is(expectedTodo.getSummary().getValue()));
                assertThat(actualResponse.getMemo(), is(expectedTodo.getMemo().getValue()));
                assertThat(actualResponse.isCompleted(), is(expectedTodo.isCompleted()));
                assertThat(actualResponse.getVersion(), is(expectedTodo.getEntityVersion()));
            }
        }

        public static class 対象のデータが存在しない場合 {

            private TodoDetail sut = null;

            private SearchTodo mockSearch = null;
            private SaveTodo mockSave = null;

            private ResponseEntity<TodoDetailResponse> actual = null;

            @Before
            public void setUp() {

                mockSearch = Mockito.mock(SearchTodo.class);
                mockSave = Mockito.mock(SaveTodo.class);

                Mockito
                    .when(mockSearch.searchById(TodoIdArgMatcher.arg(TARGET_ID)))
                    .thenReturn(Optional.empty());

                sut = new TodoDetail(mockSearch, mockSave);

                actual = sut.view(TARGET_ID);
            }

            @Test
            public void ステータスはNotFound() throws Exception {

                assertThat(actual.getStatusCode(), is(HttpStatus.NOT_FOUND));
            }
        }
    }

    @RunWith(Enclosed.class)
    public static class saveのテスト {

        public static class 対象のデータが存在して_入力内容に不備がない場合 {

            private TodoDetail sut = null;

            private SearchTodo mockSearch = null;
            private SaveTodo mockSave = null;
            private SaveTodo.Result mockUseCaseResult = null;

            private TodoDetailSaveRequest request = null;

            private Todo expectedTodo = null;

            private ResponseEntity<Object> actual = null;
            private Todo actualTodo = null;

            @Before
            public void setUp() {

                mockSearch = Mockito.mock(SearchTodo.class);
                mockSave = Mockito.mock(SaveTodo.class);
                mockUseCaseResult = Mockito.mock(SaveTodo.Result.class);

                final User user = TestUserUtil.createDefault();

                expectedTodo = new Todo(new TodoId(TARGET_ID), user.getUserId(), new Summary("タイトルー"), new Memo("メモ"),
                        true);
                expectedTodo.version(10l);

                Mockito
                    .when(mockSearch.searchById(TodoIdArgMatcher.arg(TARGET_ID)))
                    .thenReturn(Optional.of(expectedTodo));

                Mockito
                    .when(mockSearch.searchById(TodoIdArgMatcher.arg(TARGET_ID)))
                    .thenReturn(Optional.of(expectedTodo));
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

            @Test
            public void NoContetが返る() throws Exception {

                assertThat(actual.getStatusCode(), is(HttpStatus.NO_CONTENT));
            }

            @Test
            public void 設定された内容が保存される() throws Exception {

                assertThat(actualTodo.getId().getValue(), is(expectedTodo.getId().getValue()));
                assertThat(actualTodo.getSummary().getValue(), is(expectedTodo.getSummary().getValue()));
                assertThat(actualTodo.getMemo().getValue(), is(expectedTodo.getMemo().getValue()));
                assertThat(actualTodo.isCompleted(), is(expectedTodo.isCompleted()));
                assertThat(actualTodo.getEntityVersion(), is(expectedTodo.getEntityVersion()));
            }
        }

        public static class 対象のデータが存在して_入力内容に不備がある場合 {

            private TodoDetail sut = null;

            private SearchTodo mockSearch = null;
            private SaveTodo mockSave = null;
            private SaveTodo.Result mockUseCaseResult = null;

            private Todo expectedTodo = null;
            private ValidateErrors expectedErrors = null;

            @Before
            public void setUp() {

                mockSearch = Mockito.mock(SearchTodo.class);
                mockSave = Mockito.mock(SaveTodo.class);
                mockUseCaseResult = Mockito.mock(SaveTodo.Result.class);

                final User user = TestUserUtil.createDefault();

                expectedTodo = new Todo(new TodoId(TARGET_ID), user.getUserId(), new Summary("タイトルー"), new Memo("メモ"),
                        true);
                expectedTodo.version(10l);

                Mockito
                    .when(mockSearch.searchById(TodoIdArgMatcher.arg(TARGET_ID)))
                    .thenReturn(Optional.of(expectedTodo));

                expectedErrors = new ValidateErrors();
                expectedErrors.add(new ValidateError(ErrorInfo.Required, Summary.LABEL));
                expectedErrors.add(new ValidateError(ErrorInfo.MaxLengthOver, Memo.LABEL, 1000));

                Mockito
                    .when(mockSearch.searchById(TodoIdArgMatcher.arg(TARGET_ID)))
                    .thenReturn(Optional.of(expectedTodo));
                Mockito.when(mockUseCaseResult.getErrors()).thenReturn(expectedErrors);
                Mockito.when(mockSave.save(Mockito.any())).thenReturn(mockUseCaseResult);

                sut = new TodoDetail(mockSearch, mockSave);
            }

            @Test
            public void InvalidRequestExceptionがスローされる() {

                try {

                    final User user = TestUserUtil.createDefault();
                    sut.save(TARGET_ID, new TodoDetailSaveRequest(), new UserInfo(user));
                    fail();
                } catch (final InvalidRequestException exception) {

                    final ValidateErrors actualErrors = exception.getErrors();

                    for (int i = 0; i < actualErrors.toList().size(); i++) {

                        final String actualMessage = actualErrors.toList().get(i).getMessage();
                        final String expectedMessage = expectedErrors.toList().get(i).getMessage();
                        assertThat(actualMessage, is(expectedMessage));
                    }
                }
            }
        }

        public static class 対象のデータが存在しない場合 {

            private TodoDetail sut = null;

            private SearchTodo mockSearch = null;
            private SaveTodo mockSave = null;

            private ResponseEntity<Object> actual = null;

            @Before
            public void setUp() {

                mockSearch = Mockito.mock(SearchTodo.class);
                mockSave = Mockito.mock(SaveTodo.class);

                Mockito
                    .when(mockSearch.searchById(TodoIdArgMatcher.arg(TARGET_ID)))
                    .thenReturn(Optional.empty());

                sut = new TodoDetail(mockSearch, mockSave);

                actual = sut.save(TARGET_ID, new TodoDetailSaveRequest(), new UserInfo(TestUserUtil.createDefault()));
            }

            @Test
            public void ステータスはNOT_FOUND() {

                assertThat(actual.getStatusCode(), is(HttpStatus.NOT_FOUND));
            }
        }
    }
}
