package jp.glory.todo.context.user.domain.value;

import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import jp.glory.todo.context.user.domain.value.UserId;

public class UserIdArgMatcher implements ArgumentMatcher<UserId> {

    private final UserId value;

    private UserIdArgMatcher(final UserId value) {

        this.value = value;
    }

    @Override
    public boolean matches(final UserId argument) {

        return value.getValue().equals(argument.getValue());
    }

    public static UserId arg(final long loginIdValue) {

        return Mockito.argThat(new UserIdArgMatcher(new UserId(loginIdValue)));
    }
}
