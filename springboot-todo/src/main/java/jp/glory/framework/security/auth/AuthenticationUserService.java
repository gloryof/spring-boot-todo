package jp.glory.framework.security.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import jp.glory.domain.user.entity.User;
import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.Password;
import jp.glory.domain.user.value.UserId;
import jp.glory.domain.user.value.UserName;
import jp.glory.web.session.UserInfo;

/**
 * ユーザ認証処理.
 * @author Junki Yamada
 *
 */
public class AuthenticationUserService implements UserDetailsService {

    /**
     * パスワードエンコーダ.
     */
    private final PasswordEncoder encoder;

    /**
     * コンストラクタ.
     * @param encoder エンコーダ
     */
    public AuthenticationUserService(final PasswordEncoder encoder) {

        this.encoder = encoder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        if (!"test-user".equals(username)) {

            throw new UsernameNotFoundException("username is [" + username + "]");
        }

        final Password password = new Password(encoder.encode("password"));
        final User user = new User(new UserId(1l), new LoginId("test-user"), new UserName("テストユーザ"), password);

        final UserInfo authenticatedInfo = new UserInfo(user);

        return authenticatedInfo;
    }

}
