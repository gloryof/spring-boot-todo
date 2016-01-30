package jp.glory.test.validate;

import jp.glory.domain.common.error.ValidateError;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class ValidateMatcher extends BaseMatcher<ValidateError> {

    private final ValidateError expected;

    private ValidateError acutual;

    private boolean differentType = true;

    public ValidateMatcher(final ValidateError paramExpected) {

        expected = paramExpected;
    }

    @Override
    public boolean matches(Object actualParam) {

        if (actualParam == null) {

            return false;
        }

        if (!(actualParam instanceof ValidateError)) {

            return false;
        }
        differentType = false;


        acutual = (ValidateError) actualParam;
        if (!expected.getMessage().equals(acutual.getMessage())) {

            return false;
        }

        return true;
    }

    @Override
    public void describeTo(Description d) {

        d.appendValue(expected.getMessage());
        if (acutual == null) {

            d.appendText(" but actual is null.");
            return;
        }

        if (differentType) {

            d.appendText(" but actual is diffrent type.");
            return;
        }

        d.appendText(" but actual is ");
        d.appendValue(acutual.getMessage());
    }

    public static Matcher<ValidateError> validatedBy(final ValidateError paramValidateError) {
        return new ValidateMatcher(paramValidateError);
    }
}