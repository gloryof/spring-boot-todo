package jp.glory.test.validate;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Optional;
import jp.glory.domain.common.error.ValidateError;
import jp.glory.domain.common.error.ValidateErrors;

public class ValidateErrorsHelper {

    private final ValidateErrors actual;

    public ValidateErrorsHelper(final ValidateErrors paramActual) {

        actual = paramActual;
    }

    public void assertErrors(final List<ValidateError> expectedErrorList) {

        assertThat(actual.toList().size(), is(expectedErrorList.size()));

        expectedErrorList.forEach(this::assertExists);
    }

    private void assertExists(final ValidateError expected) {

        final Optional<ValidateError> matchValue =
                actual.toList().stream()
                        .filter(v -> v.isSame(expected))
                        .findFirst();

        if (!matchValue.isPresent()) {

            fail("exptected is [" + expected.getMessage() + "].But actual is null");
        }

        assertThat(matchValue.get(), is(ValidateMatcher.validatedBy(expected)));
    }
}