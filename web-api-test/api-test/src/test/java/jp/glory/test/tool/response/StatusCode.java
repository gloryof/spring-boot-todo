package jp.glory.test.tool.response;

public enum StatusCode {

    Ok(200),
    Created(201),
    Found(302),
    BadRequest(400);

    private final int value;

    private StatusCode(final int value) {

        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
