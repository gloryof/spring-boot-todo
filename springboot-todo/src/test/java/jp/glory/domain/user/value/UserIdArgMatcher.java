package jp.glory.domain.user.value;

import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

public class UserIdArgMatcher extends ArgumentMatcher<UserId> {

    private final UserId value;

    private UserIdArgMatcher(final UserId value) {

        this.value = value;
    }

    @Override
    public boolean matches(final Object argument) {

        if (argument instanceof UserId) {

            final UserId compare = (UserId) argument;

            return value.getValue().equals(compare.getValue());
        }

        return false;
    }

    public static UserId arg(final long loginIdValue) {

        return Mockito.argThat(new UserIdArgMatcher(new UserId(loginIdValue)));
    }
}
