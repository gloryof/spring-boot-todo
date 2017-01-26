package jp.glory.api.todos;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;
import jp.glory.api.ApiPaths;
import jp.glory.api.todos.request.TodoPutRequest;
import jp.glory.test.tool.page.TodoRegisterPage;
import jp.glory.test.tool.request.HeaderValues;
import jp.glory.test.tool.response.StatusCode;
import jp.glory.test.tool.script.login.LoginResult;
import jp.glory.test.tool.script.login.LoginScript;
import jp.glory.test.tool.script.todo.Summary;
import jp.glory.test.tool.script.todo.TodoData;
import jp.glory.test.tool.script.todo.TodoRegisterResult;
import jp.glory.test.tool.script.todo.TodoScript;
import jp.glory.test.tool.setup.Setup;

@RunWith(Enclosed.class)
public class TodoDetailTest {

    @BeforeClass
    public static void setup() {

        Setup.setup();
    }

    public static class Get {

        private static LoginResult loginResult = null;
        private static TodoRegisterResult todoResults = null;

        @BeforeClass
        public static void setup() {

            loginResult = LoginScript.register("todo-get-").login();

            final List<TodoData> datas = IntStream.rangeClosed(1, 5).mapToObj(v -> {

                final TodoData data = new TodoData(new Summary("テスト" + v));
                if (v % 2 == 0) {

                    data.complete();
                }

                return data;
            }).collect(Collectors.toList());

            todoResults = TodoScript.todos(loginResult.getSessionFilter(), datas).create();
        }

        @Test
        public void 指定したIDのTODOが取得できる() {

            final SessionFilter filter = loginResult.getSessionFilter();
            final TodoRegisterPage page = new TodoRegisterPage(filter);

            final HeaderValues headers = new HeaderValues();
            headers.setToken(page.getToken());

            final TodoData expected = todoResults.get(3);

            final int id = expected.getId().get();
            final Response response = given()
                                            .filter(filter)
                                        .when()
                                            .get(ApiPaths.Todo.PATH + "/" + id)
                                        .andReturn();

            response.then()
                .statusCode(StatusCode.Ok.getValue())
                .body("id", is(expected.getId().get()))
                .body("completed", is(expected.isCompleted()))
                .body("memo", is(expected.getMemo().getValue()))
                .body("summary", is(expected.getSummary().getValue()))
                .body("version", is(expected.getVersion().get()));
        }

        @Test
        public void 存在しないTODOの場合はNOT_FOUNDが帰ってくる() {

            final SessionFilter filter = loginResult.getSessionFilter();
            final TodoRegisterPage page = new TodoRegisterPage(filter);

            final HeaderValues headers = new HeaderValues();
            headers.setToken(page.getToken());

            given()
                .filter(filter)
            .when()
                .get(ApiPaths.Todo.PATH + "/" + Integer.MAX_VALUE)
            .then()
                .statusCode(StatusCode.NotFound.getValue());
        }
    }

    public static class Put {

        private static LoginResult loginResult = null;
        private static TodoRegisterResult todoResults = null;

        @BeforeClass
        public static void setup() {

            loginResult = LoginScript.register("todo-put-").login();

            final List<TodoData> datas = IntStream.rangeClosed(1, 5).mapToObj(v -> {

                final TodoData data = new TodoData(new Summary("テスト" + v));
                if (v % 2 == 0) {

                    data.complete();
                }

                return data;
            }).collect(Collectors.toList());

            todoResults = TodoScript.todos(loginResult.getSessionFilter(), datas).create();
        }

        @Test
        public void 入力チェックエラーがない場合はTODOが更新される() {

            final SessionFilter filter = loginResult.getSessionFilter();
            final TodoRegisterPage page = new TodoRegisterPage(filter);

            final HeaderValues headers = new HeaderValues();
            headers.setToken(page.getToken());

            final TodoData beforeUpdate = todoResults.get(2);

            final int id = beforeUpdate.getId().get();
            final int version = beforeUpdate.getVersion().get();
            final TodoPutRequest request = page.createValidPutRequest(id, version, !beforeUpdate.isCompleted());

            given()
                .formParams(request.toMap())
                .headers(headers.toMap())
                .filter(filter)
                .log().all()
            .when()
                .put(ApiPaths.Todo.PATH + "/" + id)
            .then()
                .statusCode(StatusCode.NoContent.getValue());

            final Response response = given()
                                            .filter(filter)
                                        .when()
                                            .get(ApiPaths.Todo.PATH + "/" + id)
                                        .andReturn();

            response.then()
                .statusCode(StatusCode.Ok.getValue())
                .body("id", is(request.getId()))
                .body("completed", is(request.isCompleted()))
                .body("memo", is(request.getMemo()))
                .body("summary", is(request.getSummary()))
                .body("version", is(version + 1));
        }

        @Test
        public void 入力チェックエラーがある場合はTODOが更新されない() {

            final SessionFilter filter = loginResult.getSessionFilter();
            final TodoRegisterPage page = new TodoRegisterPage(filter);

            final HeaderValues headers = new HeaderValues();
            headers.setToken(page.getToken());

            final TodoData beforeUpdate = todoResults.get(4);

            final int id = beforeUpdate.getId().get();
            final int version = beforeUpdate.getVersion().get();
            final TodoPutRequest request = page.createInvalidPutRequest(id, version, !beforeUpdate.isCompleted());

            given()
                .formParams(request.toMap())
                .headers(headers.toMap())
                .filter(filter)
                .log().all()
            .when()
                .put(ApiPaths.Todo.PATH + "/" + id)
            .then()
                .statusCode(StatusCode.BadRequest.getValue())
                .body("errors.size()", greaterThanOrEqualTo(1));

            final Response response = given()
                                            .filter(filter)
                                        .when()
                                            .get(ApiPaths.Todo.PATH + "/" + id)
                                        .andReturn();

            response.then()
                .statusCode(StatusCode.Ok.getValue())
                .body("id", is(beforeUpdate.getId().get()))
                .body("completed", is(beforeUpdate.isCompleted()))
                .body("memo", is(beforeUpdate.getMemo().getValue()))
                .body("summary", is(beforeUpdate.getSummary().getValue()))
                .body("version", is(beforeUpdate.getVersion().get()));
        }

    }
}
