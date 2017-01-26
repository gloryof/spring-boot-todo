package jp.glory.test.tool.page;

import static io.restassured.RestAssured.given;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.restassured.filter.session.SessionFilter;
import io.restassured.path.xml.XmlPath;
import jp.glory.api.todos.request.TodoPostRequest;
import jp.glory.api.todos.request.TodoPutRequest;
import jp.glory.test.tool.request.CsrfToken;
import jp.glory.test.tool.request.TokenFinder;

public class TodoRegisterPage {

    private final XmlPath path;

    public TodoRegisterPage(final SessionFilter filter) {

        this.path = given().filter(filter).when().get("/todos").andReturn().htmlPath();
    }

    public CsrfToken getToken() {

        return new TokenFinder(path).find();
    }

    public TodoPostRequest createValidRequest() {

        final TodoPostRequest request = new TodoPostRequest();

        final LocalDateTime time = LocalDateTime.now();

        request.setSummary("summary" + time.format(DateTimeFormatter.ofPattern("uuuuMMdd-hhmm")));
        request.setCompleted(true);
        request.setMemo(
                "memo" + time.toString() + "\n" +
                "2lines" + time.toString() + "\n" +
                "3lines" + time.toString() + "\n"
                );

        return request;
    }

    public TodoPutRequest createValidPutRequest(final int id,final int version,  final boolean completed) {

        final TodoPutRequest request = new TodoPutRequest(id, version);

        final LocalDateTime time = LocalDateTime.now();

        request.setSummary("update" + time.format(DateTimeFormatter.ofPattern("uuuuMMdd-hhmm")));
        request.setCompleted(completed);
        request.setMemo(
                "update" + time.toString() + "\n" +
                "update" + time.toString() + "\n" +
                "update" + time.toString() + "\n"
                );

        return request;
    }

    public TodoPostRequest createEmptyRequest() {

        return new TodoPostRequest();
    }

    public TodoPutRequest createInvalidPutRequest(final int id,final int version, final boolean completed) {

        final TodoPutRequest request = new TodoPutRequest(id, version);

        request.setSummary(null);
        request.setCompleted(completed);
        request.setMemo(null);

        return request;
    }
}
