package jp.glory.test.tool.request;

import io.restassured.path.xml.XmlPath;

public class TokenFinder {

    private final XmlPath path;

    public TokenFinder(final XmlPath path) {

        this.path = path;
    }

    public CsrfToken find() {

        final String value = path.get("html.body.article.@csrf-token");

        if (value == null || value.isEmpty()) {

            throw new IllegalStateException("token is not found.");
        }

        return new CsrfToken(value);
    }
}
