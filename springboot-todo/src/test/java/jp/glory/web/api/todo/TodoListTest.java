package jp.glory.web.api.todo;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jp.glory.domain.common.error.ErrorInfo;
import jp.glory.domain.common.error.ValidateError;
import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.domain.todo.entity.Todo;
import jp.glory.domain.todo.entity.Todos;
import jp.glory.domain.todo.value.Memo;
import jp.glory.domain.todo.value.Summary;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.domain.user.value.UserIdArgMatcher;
import jp.glory.framework.web.exception.InvalidRequestException;
import jp.glory.test.util.TestUserUtil;
import jp.glory.usecase.todo.SaveTodo;
import jp.glory.usecase.todo.SearchTodo;
import jp.glory.web.api.todo.request.TodoCreateRequest;
import jp.glory.web.api.todo.response.TodoCreateSuccessResponse;
import jp.glory.web.api.todo.response.TodoListResponse;
import jp.glory.web.api.todo.response.TodoStatistics;
import jp.glory.web.session.UserInfo;

@RunWith(Enclosed.class)
public class TodoListTest {

    @RunWith(Enclosed.class)
    public static class listのテスト {

        public static class 件数が0件の場合 {

            private TodoList sut = null;

            private SearchTodo mockSearch = null;
            private SaveTodo mockSave = null;

            private UserInfo userInfo = null;

            private ResponseEntity<TodoListResponse> actual = null;
            private TodoListResponse actualResponse = null;

            @Before
            public void setUp() {

                mockSearch = Mockito.mock(SearchTodo.class);
                mockSave = Mockito.mock(SaveTodo.class);

                userInfo = new UserInfo(TestUserUtil.createDefault());

                Mockito
                    .when(mockSearch.searchTodosByUser(UserIdArgMatcher.arg(userInfo.getUserId().getValue())))
                    .thenReturn(new Todos(new ArrayList<>()));

                sut = new TodoList(mockSearch, mockSave);

                actual = sut.list(userInfo);
                actualResponse = actual.getBody();
            }

            @Test
            public void ステータスはOK() {

                assertThat(actual.getStatusCode(), is(HttpStatus.OK));
            }

            @Test
            public void リストは空() throws Exception {

                assertThat(actualResponse.getDetails().isEmpty(), is(true));
            }

            @Test
            public void 統計はすべて0件() throws Exception {

                final TodoStatistics actualStatistics = actualResponse.getStatistics();

                assertThat(actualStatistics.getTotal(), is(0));
                assertThat(actualStatistics.getUnexecuted(), is(0));
                assertThat(actualStatistics.getExecuted(), is(0));
            }
        }

        public static class 実行済みのみの場合 {

            private TodoList sut = null;

            private SearchTodo mockSearch = null;
            private SaveTodo mockSave = null;

            private UserInfo userInfo = null;

            private ResponseEntity<TodoListResponse> actual = null;
            private TodoListResponse actualResponse = null;
            private TodoStatistics actualStatistics = null;

            @Before
            public void setUp() {

                mockSearch = Mockito.mock(SearchTodo.class);
                mockSave = Mockito.mock(SaveTodo.class);

                userInfo = new UserInfo(TestUserUtil.createDefault());

                final List<Todo> todoList = LongStream.rangeClosed(1, 1)
                    .mapToObj(v -> new Todo(new TodoId(v), userInfo.getUserId(), Summary.empty(), Memo.empty(), true))
                    .collect(Collectors.toList());

                Mockito
                    .when(mockSearch.searchTodosByUser(UserIdArgMatcher.arg(userInfo.getUserId().getValue())))
                    .thenReturn(new Todos(todoList));

                sut = new TodoList(mockSearch, mockSave);

                actual = sut.list(userInfo);
                actualResponse = actual.getBody();
                actualStatistics = actualResponse.getStatistics();
            }

            @Test
            public void ステータスはOK() {

                assertThat(actual.getStatusCode(), is(HttpStatus.OK));
            }

            @Test
            public void リストは1件() throws Exception {

                assertThat(actualResponse.getDetails().size(), is(1));
            }

            @Test
            public void トータルは1件() throws Exception {

                assertThat(actualStatistics.getTotal(), is(1));
            }

            @Test
            public void 実行済みは1件() throws Exception {

                assertThat(actualStatistics.getExecuted(), is(1));
            }

            @Test
            public void 未実行は0件() throws Exception {

                assertThat(actualStatistics.getUnexecuted(), is(0));
            }
        }

        public static class 未行済のみの場合 {

            private TodoList sut = null;

            private SearchTodo mockSearch = null;
            private SaveTodo mockSave = null;

            private UserInfo userInfo = null;

            private ResponseEntity<TodoListResponse> actual = null;
            private TodoListResponse actualResponse = null;
            private TodoStatistics actualStatistics = null;

            @Before
            public void setUp() {

                mockSearch = Mockito.mock(SearchTodo.class);
                mockSave = Mockito.mock(SaveTodo.class);

                userInfo = new UserInfo(TestUserUtil.createDefault());

                final List<Todo> todoList = LongStream.rangeClosed(1, 1)
                    .mapToObj(v -> new Todo(new TodoId(v), userInfo.getUserId(), Summary.empty(), Memo.empty(), false))
                    .collect(Collectors.toList());

                Mockito
                    .when(mockSearch.searchTodosByUser(UserIdArgMatcher.arg(userInfo.getUserId().getValue())))
                    .thenReturn(new Todos(todoList));

                sut = new TodoList(mockSearch, mockSave);

                actual = sut.list(userInfo);
                actualResponse = actual.getBody();
                actualStatistics = actualResponse.getStatistics();
            }

            @Test
            public void ステータスはOK() {

                assertThat(actual.getStatusCode(), is(HttpStatus.OK));
            }

            @Test
            public void リストは1件() throws Exception {

                assertThat(actualResponse.getDetails().size(), is(1));
            }

            @Test
            public void トータルは1件() throws Exception {

                assertThat(actualStatistics.getTotal(), is(1));
            }

            @Test
            public void 実行済みは0件() throws Exception {

                assertThat(actualStatistics.getExecuted(), is(0));
            }

            @Test
            public void 未実行は1件() throws Exception {

                assertThat(actualStatistics.getUnexecuted(), is(1));
            }
        }

        public static class 実行済み3件_未実行2件の場合 {

            private TodoList sut = null;

            private SearchTodo mockSearch = null;
            private SaveTodo mockSave = null;

            private UserInfo userInfo = null;

            private ResponseEntity<TodoListResponse> actual = null;
            private TodoListResponse actualResponse = null;
            private TodoStatistics actualStatistics = null;

            @Before
            public void setUp() {

                mockSearch = Mockito.mock(SearchTodo.class);
                mockSave = Mockito.mock(SaveTodo.class);

                userInfo = new UserInfo(TestUserUtil.createDefault());

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
                actualStatistics = actualResponse.getStatistics();
            }

            @Test
            public void ステータスはOK() {

                assertThat(actual.getStatusCode(), is(HttpStatus.OK));
            }

            @Test
            public void リストは5件() throws Exception {

                assertThat(actualResponse.getDetails().size(), is(5));
            }

            @Test
            public void トータルは5件() throws Exception {

                assertThat(actualStatistics.getTotal(), is(5));
            }

            @Test
            public void 実行済みは3件() throws Exception {

                assertThat(actualStatistics.getExecuted(), is(3));
            }

            @Test
            public void 未実行は2件() throws Exception {

                assertThat(actualStatistics.getUnexecuted(), is(2));
            }
        }
    }

    @RunWith(Enclosed.class)
    public static class createtTodoのテスト {


        public static class 入力内容に不備がない場合 {

            private TodoList sut = null;

            private SearchTodo mockSearch = null;
            private SaveTodo mockSave = null;

            private UserInfo userInfo = null;
            private SaveTodo.Result mockUseCaseResult;

            private ResponseEntity<TodoCreateSuccessResponse> actual = null;

            @Before
            public void setUp() {

                final TodoId expectedTodoId = new TodoId(1000l);

                mockSave = Mockito.mock(SaveTodo.class);
                mockUseCaseResult = Mockito.mock(SaveTodo.Result.class);

                Mockito.when(mockUseCaseResult.getErrors()).thenReturn(new ValidateErrors());
                Mockito.when(mockUseCaseResult.getSavedTodoId()).thenReturn(expectedTodoId);
                Mockito.when(mockSave.save(Mockito.any())).thenReturn(mockUseCaseResult);

                sut = new TodoList(mockSearch, mockSave);

                userInfo = new UserInfo(TestUserUtil.createDefault());
                actual = sut.save(new TodoCreateRequest(), userInfo);
            }


            @Test
            public void ステータスはCREATED() {

                assertThat(actual.getStatusCode(), is(HttpStatus.CREATED));
            }
        }

        public static class 入力内容に不備がある場合 {


            private TodoList sut = null;

            private SearchTodo mockSearch = null;
            private SaveTodo mockSave = null;

            private UserInfo userInfo = null;
            private SaveTodo.Result mockUseCaseResult;
            private ValidateErrors expectedErrors = null;

            @Before
            public void setUp() {

                final TodoId expectedTodoId = new TodoId(1000l);

                mockSave = Mockito.mock(SaveTodo.class);
                mockUseCaseResult = Mockito.mock(SaveTodo.Result.class);

                expectedErrors = new ValidateErrors();
                expectedErrors.add(new ValidateError(ErrorInfo.Required, Summary.LABEL));
                expectedErrors.add(new ValidateError(ErrorInfo.MaxLengthOver, Memo.LABEL, 1000));

                Mockito.when(mockUseCaseResult.getErrors()).thenReturn(expectedErrors);
                Mockito.when(mockUseCaseResult.getSavedTodoId()).thenReturn(expectedTodoId);
                Mockito.when(mockSave.save(Mockito.any())).thenReturn(mockUseCaseResult);

                userInfo = new UserInfo(TestUserUtil.createDefault());
                sut = new TodoList(mockSearch, mockSave);
            }

            @Test
            public void InvalidRequestExceptionがスローされる() {

                try {

                    sut.save(new TodoCreateRequest(), userInfo);
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
    }

}
