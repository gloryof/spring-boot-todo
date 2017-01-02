package jp.glory.api.account;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import io.restassured.filter.session.SessionFilter;
import jp.glory.api.ApiPaths;
import jp.glory.api.account.request.AccountPostRequest;
import jp.glory.common.login.LoginExecutor;
import jp.glory.common.login.LoginResult;
import jp.glory.common.request.HeaderValues;
import jp.glory.common.response.StatusCode;
import jp.glory.common.setup.Setup;
import jp.glory.page.AccountPage;

@RunWith(Enclosed.class)
public class Account {

    @BeforeClass
    public static void setup() {

        Setup.setup();
    }

    public static class Post {

        @Test
        public void 入力チェックエラーがない場合はアカウントが作成されてログインができる() {

            final SessionFilter filter = new SessionFilter();
            final AccountPage accountPage = new AccountPage(filter);

            final AccountPostRequest request = accountPage.createValidRequest();

            final HeaderValues headers = new HeaderValues();
            headers.setToken(accountPage.getToken());

            given()
                .formParams(request.toMap())
                .headers(headers.toMap())
                .filter(filter)
            .when()
                .post(ApiPaths.Account.PATH)
            .then()
                .statusCode(StatusCode.Created.getValue());

            final LoginExecutor loginPage = new LoginExecutor(filter);

            final LoginResult respones = loginPage.login(request.getLoginId(), request.getPassword());
            assertTrue(respones.isSuccess());
        }

        @Test
        public void 入力チェックエラーがある場合は400エラーになる() {


            final SessionFilter filter = new SessionFilter();
            final AccountPage accountPage = new AccountPage(filter);

            final AccountPostRequest request = accountPage.createEmptyRequest();

            final HeaderValues headers = new HeaderValues();
            headers.setToken(accountPage.getToken());

            given()
                .formParams(request.toMap())
                .headers(headers.toMap())
                .filter(filter)
            .when()
                .post(ApiPaths.Account.PATH)
            .then()
                .log().all()
                .statusCode(StatusCode.BadRequest.getValue())
                .body("errors.size()", greaterThan(1));

        }
    }
}
