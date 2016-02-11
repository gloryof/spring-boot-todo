package jp.glory.framework.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

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

        builder.inMemoryAuthentication().withUser("test-user").password("password").roles("USER");
    }
}
