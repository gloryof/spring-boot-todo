package jp.glory.web.api.account;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jp.glory.SpringbootTodoApplication;
import jp.glory.domain.common.error.ErrorInfo;
import jp.glory.domain.common.error.ValidateError;
import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.LoginIdArgMatcher;
import jp.glory.domain.user.value.Password;
import jp.glory.domain.user.value.PasswordArgMatcher;
import jp.glory.domain.user.value.UserName;
import jp.glory.domain.user.value.UserNameArgMatcher;
import jp.glory.infra.encryption.Encryption;
import jp.glory.usecase.user.CreateNewAccount;
import jp.glory.usecase.user.CreateNewAccount.Result;
import jp.glory.web.api.ApiPaths;
import jp.glory.web.api.account.request.NewAccountRequest;

@RunWith(Enclosed.class)
public class AccountTest {

    @RunWith(Enclosed.class)
    public static class アカウント作成 {

        @RunWith(SpringJUnit4ClassRunner.class)
        @SpringApplicationConfiguration(SpringbootTodoApplication.class)
        @WebAppConfiguration
        public static class postアクセス {

            @Rule
            public final MockitoRule rule = MockitoJUnit.rule();

            @InjectMocks
            private Account sut = null;

            @Mock
            private CreateNewAccount createNewAccount;

            @Mock
            private Encryption encryption;

            private MockMvc mockMvc;

            @Before
            public void setUp() {

                this.mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
            }

            @Test
            public void 入力値が正常な場合はCreatedが帰ってくる() throws Exception {

                final NewAccountRequest request = new NewAccountRequest();
                request.setLoginId("test-user");
                request.setUserName("テストユーザ");
                request.setPassword("password");

                final Password passwordData = new Password(request.getPassword());
                Mockito.when(encryption.encrypt(Mockito.anyObject())).thenReturn(passwordData);
                Mockito.when(createNewAccount.create(LoginIdArgMatcher.arg(request.getLoginId()),
                        UserNameArgMatcher.arg(request.getUserName()), PasswordArgMatcher.arg(passwordData)))
                        .thenReturn(new ResultMock(new ValidateErrors()));

                this.mockMvc.perform(TestTool.postApi(request)).andExpect(status().isCreated());
            }

            @Test
            public void 入力不備がある場合はBadRequestが帰ってくる() throws Exception {

                final NewAccountRequest request = new NewAccountRequest();
                request.setLoginId("");
                request.setUserName("");
                request.setPassword("");

                final ValidateErrors expectedErrors = new ValidateErrors();
                expectedErrors.add(new ValidateError(ErrorInfo.Required, LoginId.LABEL));
                expectedErrors.add(new ValidateError(ErrorInfo.Required, UserName.LABEL));
                expectedErrors.add(new ValidateError(ErrorInfo.Required, Password.LABEL));

                final Password passwordData = new Password(request.getPassword());
                Mockito.when(encryption.encrypt(Mockito.anyObject())).thenReturn(passwordData);
                Mockito.when(createNewAccount.create(LoginIdArgMatcher.arg(request.getLoginId()),
                        UserNameArgMatcher.arg(request.getUserName()), PasswordArgMatcher.arg(passwordData)))
                        .thenReturn(new ResultMock(expectedErrors));

                this.mockMvc.perform(TestTool.postApi(request)).andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[0]", is(expectedErrors.toList().get(0).getMessage())))
                        .andExpect(jsonPath("$.errors[1]", is(expectedErrors.toList().get(1).getMessage())))
                        .andExpect(jsonPath("$.errors[2]", is(expectedErrors.toList().get(2).getMessage())));
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
            public void getアクセス() throws Exception {

                this.mockMvc.perform(get(ApiPaths.Account.PATH)).andExpect(status().isMethodNotAllowed());
            }

            @Test
            public void putアクセス() throws Exception {

                this.mockMvc.perform(put(ApiPaths.Account.PATH)).andExpect(status().isMethodNotAllowed());
            }

            @Test
            public void deleteアクセス() throws Exception {

                this.mockMvc.perform(delete(ApiPaths.Account.PATH)).andExpect(status().isMethodNotAllowed());
            }

            @Test
            public void patchアクセス() throws Exception {

                this.mockMvc.perform(patch(ApiPaths.Account.PATH)).andExpect(status().isMethodNotAllowed());
            }
        }
    }

    private static class TestTool {

        private static RequestBuilder postApi(final NewAccountRequest request) {

            return post(ApiPaths.Account.PATH).param("loginId", request.getLoginId())
                    .param("userName", request.getUserName()).param("password", request.getPassword());
        }
    }

    private static class ResultMock extends Result {

        public ResultMock(final ValidateErrors errors) {

            super(errors);
        }
    }
}
