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

        this.login("test-user", "test-password");
    }

    public LoginResult login(final String userId, final String password) {

        final Response response =  given()
                                        .auth().form(userId, password, new FormAuthConfig("/executeLogin", "username", "password").withCsrfFieldName("_csrf"))
                                        .filter(filter)
                                    .when()
                                        .get("/todos")
                                    .andReturn();

        return new LoginResult(response);
    }
}
