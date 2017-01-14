package jp.glory.test.tool.script.login;

import io.restassured.filter.session.SessionFilter;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class LoginResult {

    private final XmlPath path;
    private final SessionFilter sessionFilter;

    public LoginResult(final Response response, final SessionFilter sessionFilter) {

        this.sessionFilter = sessionFilter;
        this.path = response.htmlPath();
    }

    public SessionFilter getSessionFilter() {

        return sessionFilter;
    }

    public boolean isSuccess() {

        return "TODO一覧".equals(path.getString("html.head.title"));
    }
}
