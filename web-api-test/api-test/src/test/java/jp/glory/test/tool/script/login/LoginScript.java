package jp.glory.test.tool.script.login;

import static io.restassured.RestAssured.given;

import io.restassured.authentication.FormAuthConfig;
import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;

public class LoginScript {

    private final LoginId loginId;
    private Password password = null;
    private SessionFilter sessionFilter = new SessionFilter();

    private LoginScript(final LoginId loginId) {

        this.loginId = loginId;
    }

    public static LoginScript as(final String loginId) {

        return new LoginScript(new LoginId(loginId));
    }

    public LoginScript password(final String password) {

        this.password = new Password(password);

        return this;
    }

    public LoginScript filter(final SessionFilter sessionFilter) {

        this.sessionFilter = sessionFilter;

        return this;
    }

    public LoginResult login() {

        if (password == null) {

            throw new IllegalStateException("password is not setuped.");
        }

        final Response response =  given()
                                        .auth()
                                            .form(loginId.getValue(),
                                                    password.getValue(),
                                                    new FormAuthConfig("/executeLogin", "username", "password").withCsrfFieldName("_csrf"))
                                        .filter(sessionFilter)
                                    .when()
                                        .get("/todos")
                                    .andReturn();

        return new LoginResult(response);
    }
}
