package jp.glory.api.todos;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.withArgs;
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
import jp.glory.api.todos.request.TodoPostRequest;
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
public class TodosTest {

    @BeforeClass
    public static void setup() {

        Setup.setup();
    }

    @RunWith(Enclosed.class)
    public static class Get {

        public static class データが複数件ある場合 {

            private static LoginResult loginResult = null;
            private static TodoRegisterResult todoResult = null;

            @BeforeClass
            public static void setup() {

                loginResult = LoginScript.register("todo-get-001-").login();

                final List<TodoData> datas = IntStream.rangeClosed(1, 5).mapToObj(v -> {

                    final TodoData data = new TodoData(new Summary("テスト" + v));
                    if (v % 2 == 0) {

                        data.complete();
                    }

                    return data;
                }).collect(Collectors.toList());

                todoResult = TodoScript.todos(loginResult.getSessionFilter(), datas).create();
            }

            @Test
            public void 詳細データの一覧が取得できる() {

                final Response response = given()
                                               .filter(loginResult.getSessionFilter())
                                           .when()
                                               .get(ApiPaths.Todo.PATH)
                                           .andReturn();

                final List<TodoData> expectTodos = todoResult.getDatas();

                response.then()
                    .body("details.size()", is(expectTodos.size()));

                for (int i = 0; i < expectTodos.size(); i++) {

                    final TodoData expected = expectTodos.get(i);
                    response.then()
                        .root("details[%s]", withArgs(i))
                            .body("id", is(expected.getId().get()))
                            .body("completed", is(expected.isCompleted()))
                            .body("memo", is(expected.getMemo().getValue()))
                            .body("summary", is(expected.getSummary().getValue()))
                            .body("version", is(1));
                }

                response.then()
                    .root("statistics")
                        .body("executed", is(2))
                        .body("unexecuted", is(3))
                        .body("total", is(5));
            }
        }
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
