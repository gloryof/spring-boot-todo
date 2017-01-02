package jp.glory.common.login;

import static io.restassured.RestAssured.given;

import io.restassured.authentication.FormAuthConfig;
import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;

public class LoginExecutor {

    private final SessionFilter filter;

    public LoginExecutor(final SessionFilter filter) {

        this.filter = filter;
    }

    public void login() {

        this.login(LoginUser.as("test-user", "test-password"));
    }

    public LoginResult login(final LoginUser user) {

        final Response response =  given()
                                        .auth()
                                            .form(user.getLoginId(),
                                                    user.getPassword(),
                                                    new FormAuthConfig("/executeLogin", "username", "password").withCsrfFieldName("_csrf"))
                                        .filter(filter)
                                    .when()
                                        .get("/todos")
                                    .andReturn();

        return new LoginResult(response);
    }
}
