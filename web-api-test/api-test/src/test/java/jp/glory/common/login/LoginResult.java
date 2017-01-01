package jp.glory.common.login;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class LoginResult {

    private final XmlPath path;

    public LoginResult(final Response response) {

        this.path = response.htmlPath();
    }

    public boolean isSuccess() {

        return "TODO一覧".equals(path.getString("html.head.title"));
    }
}
