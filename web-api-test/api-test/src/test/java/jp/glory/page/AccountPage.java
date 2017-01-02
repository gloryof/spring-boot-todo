package jp.glory.page;

import static io.restassured.RestAssured.given;

import java.time.LocalDateTime;

import io.restassured.filter.session.SessionFilter;
import io.restassured.path.xml.XmlPath;
import jp.glory.api.account.request.AccountPostRequest;
import jp.glory.common.request.CsrfToken;
import jp.glory.common.request.TokenFinder;

public class AccountPage {

    private final XmlPath path;

    public AccountPage(final SessionFilter filter) {

        this.path = given().filter(filter).when().get("/join").andReturn().htmlPath();
    }


    public CsrfToken getToken() {

        return new TokenFinder(path).find();
    }

    public AccountPostRequest createValidRequest() {

        final AccountPostRequest request = new AccountPostRequest();

        final LocalDateTime time = LocalDateTime.now();
        request.setLoginId("login-id-" + time.toString());
        request.setUserName("user-name-" + time.toString());
        request.setPassword("password-" + time.toString());

        return request;
    }

    public AccountPostRequest createEmptyRequest() {

        return new AccountPostRequest();
    }
}
