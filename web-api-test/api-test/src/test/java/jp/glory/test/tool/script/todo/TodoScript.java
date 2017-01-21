package jp.glory.test.tool.script.todo;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.stream.Collectors;

import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;
import jp.glory.api.ApiPaths;
import jp.glory.api.todos.request.TodoPostRequest;
import jp.glory.test.tool.page.TodoRegisterPage;
import jp.glory.test.tool.request.HeaderValues;
import jp.glory.test.tool.response.StatusCode;

public class TodoScript {

    private final SessionFilter filter;
    private final List<TodoData> datas;

    private TodoScript(final SessionFilter filter, final List<TodoData> datas) {

        this.filter = filter;
        this.datas = datas;
    }

    public static TodoScript todos(final SessionFilter filter, final List<TodoData> datas) {

        return new TodoScript(filter, datas);
    }

    public TodoRegisterResult create() {

        final List<TodoData> results = datas.stream().map(this::post).collect(Collectors.toList());

        return new TodoRegisterResult(results);
    }

    private TodoData post(final TodoData data) {

        final TodoRegisterPage page = new TodoRegisterPage(filter);

        final HeaderValues headers = new HeaderValues();
        headers.setToken(page.getToken());

        final TodoPostRequest request = page.createValidRequest();
        request.setMemo(data.getMemo().getValue());
        request.setSummary(data.getSummary().getValue());
        request.setCompleted(data.isCompleted());

        final Response response = given()
                                        .log().all()
                                        .formParams(request.toMap())
                                        .headers(headers.toMap())
                                        .filter(filter)
                                    .when()
                                        .post(ApiPaths.Todo.PATH)
                                    .andReturn();

        if (response.statusCode() != StatusCode.Created.getValue()) {

            throw new IllegalStateException("TODOの登録に失敗しています。");
        }

        final int id = response.getBody().jsonPath().getInt("id");

        return new TodoData(id, data);
    }
}
