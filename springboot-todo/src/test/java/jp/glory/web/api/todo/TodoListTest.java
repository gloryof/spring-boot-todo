package jp.glory.web.api.todo;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jp.glory.SpringbootTodoApplication;
import jp.glory.domain.common.error.ErrorInfo;
import jp.glory.domain.common.error.ValidateError;
import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.domain.todo.entity.Todo;
import jp.glory.domain.todo.entity.Todos;
import jp.glory.domain.todo.value.Memo;
import jp.glory.domain.todo.value.Summary;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.domain.user.value.UserId;
import jp.glory.domain.user.value.UserIdArgMatcher;
import jp.glory.test.util.TestUtil;
import jp.glory.usecase.todo.SaveTodo;
import jp.glory.usecase.todo.SearchTodo;
import jp.glory.web.api.ApiPaths;
import jp.glory.web.api.todo.request.TodoCreateRequest;
import jp.glory.web.session.UserInfo;

@RunWith(Enclosed.class)
public class TodoListTest {

    @RunWith(Enclosed.class)
    public static class todosに対するアクセス {

        private static final String TARGET_PATH = ApiPaths.Todo.PATH;

        @RunWith(Enclosed.class)
        public static class GET_is_一覧の取得 {

            @RunWith(SpringJUnit4ClassRunner.class)
            @SpringApplicationConfiguration(SpringbootTodoApplication.class)
            @WebAppConfiguration
            public static class 件数が0件の場合 {

                @Rule
                public final MockitoRule rule = MockitoJUnit.rule();

                @InjectMocks
                private TodoList sut = null;

                @Mock
                private SearchTodo mockSearch;

                @Mock
                private UserInfo mockUser;

                private MockMvc mockMvc;

                @Before
                public void setUp() {

                    final UserId userId = new UserId(100l);
                    Mockito.when(mockUser.getUserId()).thenReturn(userId);

                    Mockito
                        .when(mockSearch.searchTodosByUser(UserIdArgMatcher.arg(userId.getValue())))
                        .thenReturn(new Todos(new ArrayList<>()));

                    this.mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
                }

                @Test
                public void リストは空() throws Exception {

                    this.mockMvc.perform(get(TARGET_PATH)).andExpect(status().isOk())
                            .andExpect(TestTool.hasDetailSize(0));
                }

                @Test
                public void 統計はすべて0件() throws Exception {

                    this.mockMvc.perform(get(TARGET_PATH)).andExpect(status().isOk())
                            .andExpect(TestTool.isTotal(0))
                            .andExpect(TestTool.isExecuted(0))
                            .andExpect(TestTool.isUnexecuted(0));
                }
            }

            @RunWith(SpringJUnit4ClassRunner.class)
            @SpringApplicationConfiguration(SpringbootTodoApplication.class)
            @WebAppConfiguration
            public static class 実行済みのみの場合 {

                @Rule
                public final MockitoRule rule = MockitoJUnit.rule();

                @InjectMocks
                private TodoList sut = null;

                private MockMvc mockMvc;

                @Mock
                private SearchTodo mockSearch;

                @Mock
                private UserInfo mockUser;

                @Before
                public void setUp() {

                    final UserId userId = new UserId(100l);
                    Mockito.when(mockUser.getUserId()).thenReturn(userId);

                    final List<Todo> todoList = LongStream.rangeClosed(1, 1)
                        .mapToObj(v -> new Todo(new TodoId(v), userId, Summary.empty(), Memo.empty(), true))
                        .collect(Collectors.toList());

                    final Todos exepcetdTodos = new Todos(todoList);
                    Mockito
                        .when(mockSearch.searchTodosByUser(UserIdArgMatcher.arg(userId.getValue())))
                        .thenReturn(exepcetdTodos);

                    this.mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
                }

                @Test
                public void リストは1件() throws Exception {

                    this.mockMvc.perform(get(TARGET_PATH)).andExpect(status().isOk())
                            .andExpect(TestTool.hasDetailSize(1));
                }

                @Test
                public void トータルは1件() throws Exception {

                    this.mockMvc.perform(get(TARGET_PATH)).andExpect(status().isOk())
                            .andExpect(TestTool.isTotal(1));
                }

                @Test
                public void 実行済みは1件() throws Exception {

                    this.mockMvc.perform(get(TARGET_PATH)).andExpect(status().isOk())
                            .andExpect(TestTool.isExecuted(1));
                }

                @Test
                public void 未実行は0件() throws Exception {

                    this.mockMvc.perform(get(TARGET_PATH)).andExpect(status().isOk())
                            .andExpect(TestTool.isUnexecuted(0));
                }
            }

            @RunWith(SpringJUnit4ClassRunner.class)
            @SpringApplicationConfiguration(SpringbootTodoApplication.class)
            @WebAppConfiguration
            public static class 未行済のみの場合 {

                @Rule
                public final MockitoRule rule = MockitoJUnit.rule();

                @InjectMocks
                private TodoList sut = null;

                private MockMvc mockMvc;

                @Mock
                private SearchTodo mockSearch;

                @Mock
                private UserInfo mockUser;

                @Before
                public void setUp() {

                    final UserId userId = new UserId(100l);
                    Mockito.when(mockUser.getUserId()).thenReturn(userId);

                    final List<Todo> todoList = LongStream.rangeClosed(1, 1)
                        .mapToObj(v -> new Todo(new TodoId(v), userId, Summary.empty(), Memo.empty(), false))
                        .collect(Collectors.toList());

                    final Todos exepcetdTodos = new Todos(todoList);
                    Mockito
                        .when(mockSearch.searchTodosByUser(UserIdArgMatcher.arg(userId.getValue())))
                        .thenReturn(exepcetdTodos);

                    this.mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
                }

                @Test
                public void リストは1件() throws Exception {

                    this.mockMvc.perform(get(TARGET_PATH)).andExpect(status().isOk())
                            .andExpect(TestTool.hasDetailSize(1));
                }

                @Test
                public void トータルは1件() throws Exception {

                    this.mockMvc.perform(get(TARGET_PATH)).andExpect(status().isOk())
                            .andExpect(TestTool.isTotal(1));
                }

                @Test
                public void 実行済みは0件() throws Exception {

                    this.mockMvc.perform(get(TARGET_PATH)).andExpect(status().isOk())
                            .andExpect(TestTool.isExecuted(0));
                }

                @Test
                public void 未実行は1件() throws Exception {

                    this.mockMvc.perform(get(TARGET_PATH)).andExpect(status().isOk())
                            .andExpect(TestTool.isUnexecuted(1));
                }
            }

            @RunWith(SpringJUnit4ClassRunner.class)
            @SpringApplicationConfiguration(SpringbootTodoApplication.class)
            @WebAppConfiguration
            public static class 実行済み3件_未実行2件の場合 {

                @Rule
                public final MockitoRule rule = MockitoJUnit.rule();

                @InjectMocks
                private TodoList sut = null;

                private MockMvc mockMvc;

                @Mock
                private SearchTodo mockSearch;

                @Mock
                private UserInfo mockUser;

                @Before
                public void setUp() {

                    final UserId userId = new UserId(100l);
                    Mockito.when(mockUser.getUserId()).thenReturn(userId);

                    final List<Todo> executedList = LongStream.rangeClosed(1, 3)
                            .mapToObj(v -> new Todo(new TodoId(v), userId, Summary.empty(), Memo.empty(), true))
                            .collect(Collectors.toList());

                    final List<Todo> unexcutedList = LongStream.rangeClosed(4, 5)
                            .mapToObj(v -> new Todo(new TodoId(v), userId, Summary.empty(), Memo.empty(), false))
                            .collect(Collectors.toList());

                    final List<Todo> todoList = new ArrayList<>(executedList);
                    todoList.addAll(unexcutedList);

                    final Todos exepcetdTodos = new Todos(todoList);
                    Mockito
                        .when(mockSearch.searchTodosByUser(UserIdArgMatcher.arg(userId.getValue())))
                        .thenReturn(exepcetdTodos);

                    this.mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
                }

                @Test
                public void リストは5件() throws Exception {

                    this.mockMvc.perform(get(TARGET_PATH)).andExpect(status().isOk())
                            .andExpect(TestTool.hasDetailSize(5));
                }

                @Test
                public void トータルは5件() throws Exception {

                    this.mockMvc.perform(get(TARGET_PATH)).andExpect(status().isOk())
                            .andExpect(TestTool.isTotal(5));
                }

                @Test
                public void 実行済みは3件() throws Exception {

                    this.mockMvc.perform(get(TARGET_PATH)).andExpect(status().isOk())
                            .andExpect(TestTool.isExecuted(3));
                }

                @Test
                public void 未実行は2件() throws Exception {

                    this.mockMvc.perform(get(TARGET_PATH)).andExpect(status().isOk())
                            .andExpect(TestTool.isUnexecuted(2));
                }
            }
            
        }

        @RunWith(Enclosed.class)
        public static class POST_is_TODOの作成 {

            @RunWith(SpringJUnit4ClassRunner.class)
            @SpringApplicationConfiguration(SpringbootTodoApplication.class)
            @WebAppConfiguration
            public static class 入力内容に不備がない場合 {

                @Rule
                public final MockitoRule rule = MockitoJUnit.rule();

                @InjectMocks
                private TodoList sut = null;

                @Mock
                private SaveTodo mockSaveTodo;

                @Mock
                private SaveTodo.Result mockUseCaseResult;

                @Mock
                private UserInfo mockUser;

                private MockMvc mockMvc;

                private TodoCreateRequest request = null;
                private TodoId expectedTodoId = null;

                @Before
                public void setUp() {

                    expectedTodoId = new TodoId(1000l);
                    final UserId userId = new UserId(2000l);

                    Mockito.when(mockUseCaseResult.getErrors()).thenReturn(new ValidateErrors());
                    Mockito.when(mockUseCaseResult.getSavedTodoId()).thenReturn(expectedTodoId);
                    Mockito.when(mockSaveTodo.save(Mockito.any())).thenReturn(mockUseCaseResult);
                    Mockito.when(mockUser.getUserId()).thenReturn(userId);

                    this.mockMvc = MockMvcBuilders.standaloneSetup(sut).build();

                    request = new TodoCreateRequest();
                    request.setSummary("新規タイトル");
                    request.setMemo("新メモ");
                    request.setCompleted(false);
                }

                @Test
                public void Createdが返る() throws Exception {

                    this.mockMvc.perform(TestTool.postSaveApi(TARGET_PATH, request))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("id", is(expectedTodoId.getValue().intValue())));
                }
            }

            @RunWith(SpringJUnit4ClassRunner.class)
            @SpringApplicationConfiguration(SpringbootTodoApplication.class)
            @WebAppConfiguration
            public static class 入力内容に不備がある場合 {

                @Rule
                public final MockitoRule rule = MockitoJUnit.rule();

                @InjectMocks
                private TodoList sut = null;

                @Mock
                private SaveTodo mockSaveTodo;

                @Mock
                private SaveTodo.Result mockUseCaseResult;

                @Mock
                private UserInfo mockUser;
                
                @Autowired
                private HandlerExceptionResolver handlerExceptionResolver;

                private MockMvc mockMvc;

                private TodoCreateRequest request = null;
                private ValidateErrors expectedErrors = null;

                @Before
                public void setUp() {

                    final UserId userId = new UserId(2000l);

                    expectedErrors = new ValidateErrors();
                    expectedErrors.add(new ValidateError(ErrorInfo.Required, Summary.LABEL));
                    expectedErrors.add(new ValidateError(ErrorInfo.MaxLengthOver, Memo.LABEL, 1000));

                    Mockito.when(mockUseCaseResult.getErrors()).thenReturn(expectedErrors);
                    Mockito.when(mockSaveTodo.save(Mockito.any())).thenReturn(mockUseCaseResult);
                    Mockito.when(mockUser.getUserId()).thenReturn(userId);

                    this.mockMvc = MockMvcBuilders.standaloneSetup(sut)
                            .setHandlerExceptionResolvers(handlerExceptionResolver).build();

                    request = new TodoCreateRequest();
                    request.setSummary("");
                    request.setMemo(TestUtil.repeat("a", 1001));
                    request.setCompleted(false);
                }

                @Test
                public void BadRequestが返る() throws Exception {

                    this.mockMvc.perform(TestTool.postSaveApi(TARGET_PATH, request))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[0]", is(expectedErrors.toList().get(0).getMessage())))
                        .andExpect(jsonPath("$.errors[1]", is(expectedErrors.toList().get(1).getMessage())));
                }
            }
        }

        
        @RunWith(SpringJUnit4ClassRunner.class)
        @SpringApplicationConfiguration(SpringbootTodoApplication.class)
        @WebAppConfiguration
        public static class 許可されないアクセス {

            @Autowired
            private WebApplicationContext wac;

            private MockMvc mockMvc;

            @Before
            public void setUp() {

                this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
            }

            @Test
            public void putアクセス() throws Exception {

                this.mockMvc.perform(put(TARGET_PATH)).andExpect(status().isMethodNotAllowed());
            }

            @Test
            public void deleteアクセス() throws Exception {

                this.mockMvc.perform(delete(TARGET_PATH)).andExpect(status().isMethodNotAllowed());
            }

            @Test
            public void patchアクセス() throws Exception {

                this.mockMvc.perform(patch(TARGET_PATH)).andExpect(status().isMethodNotAllowed());
            }
        }
    }

    private static class TestTool {

        private static ResultMatcher hasDetailSize(int size) {
            
            return jsonPath("$.details", is(hasSize(size)));
        }

        private static ResultMatcher isTotal(int count) {
            
            return jsonPath("$.statictis.total", is(count));
        }

        private static ResultMatcher isExecuted(int count) {
            
            return jsonPath("$.statictis.executed", is(count));
        }

        private static ResultMatcher isUnexecuted(int count) {
            
            return jsonPath("$.statictis.unexecuted", is(count));
        }

        public static RequestBuilder postSaveApi(final String path, final TodoCreateRequest request) {
            return post(path)
                    .param("summary", request.getSummary())
                    .param("memo", request.getMemo())
                    .param("completed", String.valueOf(request.isCompleted()));
        }
    }
}
