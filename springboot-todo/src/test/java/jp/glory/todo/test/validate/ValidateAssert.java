package jp.glory.todo.test.validate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import jp.glory.todo.context.base.domain.error.ValidateError;
import jp.glory.todo.context.base.domain.error.ValidateErrors;

public class ValidateAssert {

    private final ValidateErrors expected;

    private final ValidateErrors actual;

    public ValidateAssert(final ValidateErrors expected, final ValidateErrors actual) {

        this.expected = expected;
        this.actual  = actual;
    }

    public ValidateAssert(final ValidateError expected, final ValidateErrors actual) {

        this.expected = new ValidateErrors();
        this.expected.add(expected);

        this.actual  = actual;
    }

    public void assertAll() {

        final List<ValidateError> expectedList = expected.toList();
        final List<ValidateError> actualList = actual.toList();

        assertEquals(expectedList.size(), actualList.size());

        for (int i = 0; i < expectedList.size(); i++) {

            ValidateError expectedError = expectedList.get(i);
            ValidateError actualError = actualList.get(i);

            assertEquals(expectedError.getMessage(), actualError.getMessage());
        }
    }
}
