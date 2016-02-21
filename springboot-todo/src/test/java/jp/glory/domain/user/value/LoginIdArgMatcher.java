package jp.glory.domain.user.value;

import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

public class LoginIdArgMatcher extends ArgumentMatcher<LoginId> {

    private final LoginId value;

    private LoginIdArgMatcher(final LoginId value) {

        this.value = value;
    }

    @Override
    public boolean matches(final Object argument) {

        if (argument instanceof LoginId) {

            final LoginId compare = (LoginId) argument;

            return value.getValue().equals(compare.getValue());
        }

        return false;
    }

    public static LoginId arg(final String loginIdValue) {

        return Mockito.argThat(new LoginIdArgMatcher(new LoginId(loginIdValue)));
    }
}
