package jp.glory.domain.user.value;

import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

public class PasswordArgMatcher extends ArgumentMatcher<Password> {

    private final Password value;

    private PasswordArgMatcher(final Password value) {

        this.value = value;
    }

    @Override
    public boolean matches(final Object argument) {

        if (argument instanceof Password) {

            final Password compare = (Password) argument;

            return value.getValue().equals(compare.getValue());
        }

        return false;
    }

    public static Password arg(final Password password) {

        return Mockito.argThat(new PasswordArgMatcher(password));
    }
}
