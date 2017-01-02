package jp.glory.test.tool.request;

public class CsrfToken {

    private final String value;

    public CsrfToken(final String value) {

        this.value = value;
    }

    String getValue() {
        return value;
    }
}
