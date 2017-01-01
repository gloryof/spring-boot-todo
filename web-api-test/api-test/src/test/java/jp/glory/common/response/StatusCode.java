package jp.glory.common.response;

public enum StatusCode {

    Created(201),
    Found(302);

    private final int value;

    private StatusCode(final int value) {

        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
