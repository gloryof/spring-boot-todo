package jp.glory.todo.context.user.domain.value;

import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import jp.glory.todo.context.user.domain.value.UserName;

public class UserNameArgMatcher implements ArgumentMatcher<UserName> {

    private final UserName value;

    private UserNameArgMatcher(final UserName value) {

        this.value = value;
    }

    @Override
    public boolean matches(final UserName argument) {


        return value.getValue().equals(argument.getValue());
    }

    public static UserName arg(final String userNameValue) {

        return Mockito.argThat(new UserNameArgMatcher(new UserName(userNameValue)));
    }
}
