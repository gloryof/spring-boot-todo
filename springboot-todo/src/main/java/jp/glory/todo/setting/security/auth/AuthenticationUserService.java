package jp.glory.todo.setting.security.auth;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jp.glory.todo.context.base.web.UserInfo;
import jp.glory.todo.context.user.domain.entity.RegisteredUser;
import jp.glory.todo.context.user.domain.value.LoginId;
import jp.glory.todo.context.user.usecase.SearchRegisteredUser;

/**
 * ユーザ認証処理.
 * @author Junki Yamada
 *
 */
public class AuthenticationUserService implements UserDetailsService {

    /**
     * ユーザ検索.
     */
    private final SearchRegisteredUser searchUser;

    /**
     * コンストラクタ.
     * @param repository ユーザ検索
     */
    public AuthenticationUserService(final SearchRegisteredUser searchUser) {

        this.searchUser = searchUser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        final LoginId loginId = new LoginId(username);
        final Optional<RegisteredUser> userOpt = searchUser.findBy(loginId);

        final RegisteredUser user = userOpt.orElseThrow(() -> new UsernameNotFoundException("username is [" + username + "]"));
        final UserInfo authenticatedInfo = new UserInfo(user);

        return authenticatedInfo;
    }

}
