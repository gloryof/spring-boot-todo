package jp.glory.web.page;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

@RunWith(Enclosed.class)
public class JoinPageTest {

    @RunWith(SpringJUnit4ClassRunner.class)
    @SpringApplicationConfiguration(SpringbootTodoApplication.class)
    @WebAppConfiguration
    public static class パラメータなしのアクセス {

        @Autowired
        private WebApplicationContext wac;

        private MockMvc mockMvc;

        @Before
        public void setUp() {

            this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        }

        @Test
        public void getアクセスでページが表示される() throws Exception {

            this.mockMvc.perform(get(PagePaths.Join.PATH)).andExpect(status().isOk())
                    .andExpect(content().string(containsString("<title>アカウント作成")));
        }

        @Test
        public void postアクセスでMethodNotAllowedエラーになる() throws Exception {

            this.mockMvc.perform(post(PagePaths.Join.PATH)).andExpect(status().isMethodNotAllowed());
        }

        @Test
        public void deleteアクセスでMethodNotAllowedエラーになる() throws Exception {

            this.mockMvc.perform(delete(PagePaths.Join.PATH)).andExpect(status().isMethodNotAllowed());
        }

        @Test
        public void patchアクセスでMethodNotAllowedエラーになる() throws Exception {

            this.mockMvc.perform(patch(PagePaths.Join.PATH)).andExpect(status().isMethodNotAllowed());
        }
    }
}
