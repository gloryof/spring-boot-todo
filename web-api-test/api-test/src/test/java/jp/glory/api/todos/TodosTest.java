package jp.glory.api.todos;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;
import jp.glory.api.ApiPaths;
import jp.glory.api.todos.request.TodoPostRequest;
import jp.glory.test.tool.page.TodoRegisterPage;
import jp.glory.test.tool.request.HeaderValues;
import jp.glory.test.tool.response.StatusCode;
import jp.glory.test.tool.script.login.LoginResult;
import jp.glory.test.tool.script.login.LoginScript;
import jp.glory.test.tool.setup.Setup;

@RunWith(Enclosed.class)
public class TodosTest {

    @BeforeClass
    public static void setup() {

        Setup.setup();
    }

    public static class Post {

        private static LoginResult loginResult = null;

        @BeforeClass
        public static void setup() {

            loginResult = LoginScript.register("todo-post-").login();
        }

        @Test
        public void 入力チェックエラーがない場合はTODOが作成される() {

            final SessionFilter filter = loginResult.getSessionFilter();
            final TodoRegisterPage page = new TodoRegisterPage(filter);

            final HeaderValues headers = new HeaderValues();
            headers.setToken(page.getToken());

            final TodoPostRequest request = page.createValidRequest();

            final Response response = given()
                                            .formParams(request.toMap())
                                            .headers(headers.toMap())
                                            .filter(filter)
                                        .when()
                                            .post(ApiPaths.Todo.PATH)
                                        .andReturn();

            response.then().statusCode(StatusCode.Created.getValue());

            final int id = response.getBody().jsonPath().getInt("id");
            given()
                .filter(filter)
                .when()
                    .get(ApiPaths.Todo.PATH + "/" + id)
                .then()
                    .statusCode(StatusCode.Ok.getValue());
        }

        @Test
        public void 入力チェックエラーがある場合は400エラーになる() {

            final SessionFilter filter = loginResult.getSessionFilter();
            final TodoRegisterPage page = new TodoRegisterPage(filter);

            final HeaderValues headers = new HeaderValues();
            headers.setToken(page.getToken());

            final TodoPostRequest request = page.createEmptyRequest();

            given()
                .formParams(request.toMap())
                .headers(headers.toMap())
                .filter(filter)
            .when()
                .post(ApiPaths.Todo.PATH)
            .then()
                .statusCode(StatusCode.BadRequest.getValue())
                .body("errors.size()", greaterThanOrEqualTo(1));
        }
    }

}
