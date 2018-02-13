package jp.glory.todo.context.user.domain.value;

import org.mockito.ArgumentMatcher;

public class LoginIdArgMatcher implements ArgumentMatcher<LoginId> {

    private final LoginId value;

    private LoginIdArgMatcher(final LoginId value) {

        this.value = value;
    }

    @Override
    public boolean matches(final LoginId argument) {

        return value.getValue().equals(argument.getValue());
    }
}
