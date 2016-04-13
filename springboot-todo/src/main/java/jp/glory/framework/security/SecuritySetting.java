package jp.glory.framework.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jp.glory.framework.security.auth.AuthenticationUserService;

/**
 * セキュリティ設定
 * 
 * @author Junki Yamada
 *
 */
@Configuration
public class SecuritySetting extends GlobalAuthenticationConfigurerAdapter {


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
        final AuthenticationUserService service = new AuthenticationUserService(encoder);

        provider.setPasswordEncoder(encoder);
        provider.setUserDetailsService(service);

        return provider;
    }
}
