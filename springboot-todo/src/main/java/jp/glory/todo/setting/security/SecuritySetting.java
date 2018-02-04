package jp.glory.todo.setting.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jp.glory.todo.context.user.domain.repository.UserRepository;
import jp.glory.todo.setting.security.auth.AuthenticationUserService;

/**
 * セキュリティ設定
 * 
 * @author Junki Yamada
 *
 */
@Configuration
public class SecuritySetting extends GlobalAuthenticationConfigurerAdapter {

    /**
     * ユーザ検索.
     */
    @Autowired
    private UserRepository repository;

    @Override
    public void init(final AuthenticationManagerBuilder builder) throws Exception {

        builder.authenticationProvider(createProvider());
    }

    /**
     * 認証プロバイダを作成する.
     * @return 認証プロバイダ
     */
    private AuthenticationProvider createProvider() {

        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final AuthenticationUserService service = new AuthenticationUserService(repository);

        provider.setPasswordEncoder(encoder);
        provider.setUserDetailsService(service);

        return provider;
    }
}
