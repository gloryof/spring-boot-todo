package jp.glory.test.tool.script.login;

import static io.restassured.RestAssured.given;

import io.restassured.authentication.FormAuthConfig;
import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;
import jp.glory.api.ApiPaths;
import jp.glory.api.account.request.AccountPostRequest;
import jp.glory.test.tool.page.AccountPage;
import jp.glory.test.tool.request.HeaderValues;
import jp.glory.test.tool.response.StatusCode;

public class LoginScript {

    private final LoginId loginId;
    private Password password = null;
    private SessionFilter sessionFilter = new SessionFilter();

    private LoginScript(final LoginId loginId) {

        this.loginId = loginId;
    }

    public static LoginScript register(final String prefixLabel) {

        final SessionFilter registerSession = new SessionFilter();
        final AccountPage accountPage = new AccountPage(registerSession);
        final AccountPostRequest request = accountPage.createValidRequest();

        request.setLoginId(prefixLabel + "-" + request.getLoginId());
        request.setUserName(prefixLabel + "-" + request.getUserName());
        request.setPassword(prefixLabel + "-" + request.getPassword());

        final HeaderValues headers = new HeaderValues();
        headers.setToken(accountPage.getToken());

        final Response response = given()
                                     .formParams(request.toMap())
                                     .headers(headers.toMap())
                                     .filter(registerSession)
                                 .when()
                                     .post(ApiPaths.Account.PATH)
                                 .andReturn();

        if (response.getStatusCode() != StatusCode.Created.getValue()) {

            throw new IllegalStateException("アカウント登録に失敗しました。");
        }

        return LoginScript.as(new LoginId(request.getLoginId())).password(new Password(request.getPassword()));
    }

    public static LoginScript as(final LoginId loginId) {

        return new LoginScript(loginId);
    }

    public LoginScript password(final Password password) {

        this.password = password;

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

        return new LoginResult(response, sessionFilter);
    }
}
