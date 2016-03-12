package jp.glory.web.api.todo;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jp.glory.SpringbootTodoApplication;
import jp.glory.domain.todo.entity.Todo;
import jp.glory.domain.todo.value.Memo;
import jp.glory.domain.todo.value.Summary;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.domain.todo.value.TodoIdArgMatcher;
import jp.glory.domain.user.value.UserId;
import jp.glory.usecase.todo.SearchTodo;
import jp.glory.web.api.ApiPaths;

@RunWith(Enclosed.class)
public class TodoDetailTest {

    @RunWith(Enclosed.class)
    public static class 許可されたアクセス {

        private static final long TARGET_ID = 100;
        private static final String TARGET_PATH = ApiPaths.Todo.PATH + "/" + TARGET_ID;

        @RunWith(Enclosed.class)
        public static class GET {

            public static class 対象のデータが存在する場合 {

                @Rule
                public final MockitoRule rule = MockitoJUnit.rule();

                @InjectMocks
                private TodoDetail sut = null;

                @Mock
                private SearchTodo mockSearch;

                private MockMvc mockMvc;

                private Todo expectedTodo = null;

                @Before
                public void setUp() {

                    expectedTodo = new Todo(new TodoId(TARGET_ID), new UserId(200l), new Summary("タイトルー"), new Memo("メモ"), true);
                    Mockito
                        .when(mockSearch.searchById(TodoIdArgMatcher.arg(TARGET_ID)))
                        .thenReturn(Optional.of(expectedTodo));

                    this.mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
                }

                @Test
                public void JSONにTodoの内容が設定される() throws Exception {

                    this.mockMvc.perform(get(TARGET_PATH))
                        .andExpect(status().isOk())
                        .andExpect(TestTool.isId(expectedTodo.getId()))
                        .andExpect(TestTool.isSummary(expectedTodo.getSummary()))
                        .andExpect(TestTool.isMemo(expectedTodo.getMemo()))
                        .andExpect(TestTool.isCompleted(expectedTodo.isCompleted()));
                }
            }
            public static class 対象のデータが存在しない場合 {

                @Rule
                public final MockitoRule rule = MockitoJUnit.rule();

                @InjectMocks
                private TodoDetail sut = null;

                @Mock
                private SearchTodo mockSearch;

                private MockMvc mockMvc;

                @Before
                public void setUp() {

                    Mockito
                        .when(mockSearch.searchById(TodoIdArgMatcher.arg(TARGET_ID)))
                        .thenReturn(Optional.empty());

                    this.mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
                }

                @Test
                public void NotFoundが返る() throws Exception {

                    this.mockMvc.perform(get(TARGET_PATH))
                        .andExpect(status().isNotFound());
                }
            }
        }
    }
    @RunWith(SpringJUnit4ClassRunner.class)
    @SpringApplicationConfiguration(SpringbootTodoApplication.class)
    @WebAppConfiguration
    public static class 許可されないアクセス {

        private static final long TARGET_ID = 100;
        private static final String TARGET_PATH = ApiPaths.Todo.PATH + "/" + TARGET_ID;

        @Autowired
        private WebApplicationContext wac;

        private MockMvc mockMvc;

        @Before
        public void setUp() {

            this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        }

        @Test
        public void postアクセス() throws Exception {

            this.mockMvc.perform(post(TARGET_PATH)).andExpect(status().isMethodNotAllowed());
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

    private static class TestTool {

        public static ResultMatcher isId(final TodoId id) {

            return jsonPath("$.id", is(id.getValue().intValue()));
        }

        public static ResultMatcher isSummary(final Summary summary) {

            return jsonPath("$.summary", is(summary.getValue()));
        }

        public static ResultMatcher isMemo(final Memo memo) {

            return jsonPath("$.memo", is(memo.getValue()));
        }

        public static ResultMatcher isCompleted(final boolean completed) {

            return jsonPath("$.completed", is(completed));
        }
    }
}
