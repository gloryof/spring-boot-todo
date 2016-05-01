package jp.glory.framework.security.auth;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jp.glory.domain.user.entity.User;
import jp.glory.domain.user.repository.UserRepository;
import jp.glory.domain.user.value.LoginId;
import jp.glory.web.session.UserInfo;

/**
 * ユーザ認証処理.
 * @author Junki Yamada
 *
 */
public class AuthenticationUserService implements UserDetailsService {

    /**
     * ユーザリポジトリ.
     */
    private final UserRepository repository;

    /**
     * コンストラクタ.
     * @param repository ユーザリポジトリ
     */
    public AuthenticationUserService(final UserRepository repository) {

        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        final LoginId loginId = new LoginId(username);
        final Optional<User> userOpt = repository.findBy(loginId);

        final User user = userOpt.orElseThrow(() -> new UsernameNotFoundException("username is [" + username + "]"));
        final UserInfo authenticatedInfo = new UserInfo(user);

        return authenticatedInfo;
    }

}
