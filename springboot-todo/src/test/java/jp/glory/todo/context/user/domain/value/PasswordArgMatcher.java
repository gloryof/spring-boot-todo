package jp.glory.todo.context.user.domain.value;

import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import jp.glory.todo.context.user.domain.value.Password;

public class PasswordArgMatcher implements ArgumentMatcher<Password> {

    private final Password value;

    private PasswordArgMatcher(final Password value) {

        this.value = value;
    }

    @Override
    public boolean matches(final Password argument) {

        final Password compare = (Password) argument;

        return value.getValue().equals(compare.getValue());
    }

    public static Password arg(final Password password) {

        return Mockito.argThat(new PasswordArgMatcher(password));
    }
}
