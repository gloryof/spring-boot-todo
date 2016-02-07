package jp.glory.web.session;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import jp.glory.domain.user.entity.User;
import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.Password;
import jp.glory.domain.user.value.UserId;
import jp.glory.domain.user.value.UserName;

public class UserInfoTest {

    private UserInfo sut = null;
    private User userData = null;

    @Before
    public void setUp() {

        sut = new UserInfo();
        userData = new User(new UserId(1000l), new LoginId("login-user"), new UserName("ユーザ名"),
                new Password("password"));
    }

    @Test
    public void activateでセッションが有効化される() {

        assertSessionInactive(sut);

        sut.activate(userData);

        assertSessionActive(sut, userData);
    }

    @Test
    public void inactivateでセッションが無効化される() {

        sut.activate(userData);
        assertSessionActive(sut, userData);

        sut.inactivate();
        assertSessionInactive(sut);
    }

    private void assertSessionInactive(final UserInfo sut) {

        assertThat(sut.isActivate(), is(false));
        assertThat(sut.getUserId(), is(nullValue()));
        assertThat(sut.getName(), is(nullValue()));
    }

    private void assertSessionActive(final UserInfo sut, final User expected) {

        assertThat(sut.isActivate(), is(true));
        assertThat(sut.getUserId().isSame(expected.getUserId()), is(true));
        assertThat(sut.getName().getValue(), is(expected.getUserName().getValue()));
    }
}
