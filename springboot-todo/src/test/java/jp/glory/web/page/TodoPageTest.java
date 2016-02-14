package jp.glory.web.page;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jp.glory.SpringbootTodoApplication;
import jp.glory.domain.user.entity.User;
import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.Password;
import jp.glory.domain.user.value.UserId;
import jp.glory.domain.user.value.UserName;
import jp.glory.web.session.UserInfo;

@RunWith(Enclosed.class)
public class TodoPageTest {

    @RunWith(SpringJUnit4ClassRunner.class)
    @SpringApplicationConfiguration(SpringbootTodoApplication.class)
    @WebAppConfiguration
    public static class パラメータなしのアクセス {

        @Autowired
        private WebApplicationContext wac;

        @Autowired
        private UserInfo userInfo;

        private MockMvc mockMvc = null;

        @Before
        public void setUp() {

            this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
            this.userInfo.activate(new User(new UserId(1000l), new LoginId("login-user"), new UserName("テスト"),
                    new Password("password")));
        }

        @Test
        public void getアクセスでページが表示される() throws Exception {

            this.mockMvc.perform(get(PagePaths.Todo.PATH)).andExpect(status().isOk())
                    .andExpect(content().string(containsString("<title>TODO一覧")));
        }

        @Test
        public void postアクセスでMethodNotAllowedエラーになる() throws Exception {

            this.mockMvc.perform(post(PagePaths.Todo.PATH)).andExpect(status().isMethodNotAllowed());
        }

        @Test
        public void putアクセスでMethodNotAllowedエラーになる() throws Exception {

            this.mockMvc.perform(put(PagePaths.Todo.PATH)).andExpect(status().isMethodNotAllowed());
        }

        @Test
        public void deleteアクセスでMethodNotAllowedエラーになる() throws Exception {

            this.mockMvc.perform(delete(PagePaths.Todo.PATH)).andExpect(status().isMethodNotAllowed());
        }

        @Test
        public void patchアクセスでMethodNotAllowedエラーになる() throws Exception {

            this.mockMvc.perform(patch(PagePaths.Todo.PATH)).andExpect(status().isMethodNotAllowed());
        }
    }
}
