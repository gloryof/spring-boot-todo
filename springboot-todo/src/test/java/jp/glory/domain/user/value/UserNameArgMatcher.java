package jp.glory.domain.user.value;

import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

public class UserNameArgMatcher extends ArgumentMatcher<UserName> {

    private final UserName value;

    private UserNameArgMatcher(final UserName value) {

        this.value = value;
    }

    @Override
    public boolean matches(final Object argument) {

        if (argument instanceof UserName) {

            final UserName compare = (UserName) argument;

            return value.getValue().equals(compare.getValue());
        }

        return false;
    }

    public static UserName arg(final String userNameValue) {

        return Mockito.argThat(new UserNameArgMatcher(new UserName(userNameValue)));
    }
}
