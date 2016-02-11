package jp.glory.framework.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import jp.glory.web.page.PagePaths;

/**
 * Webセキュリティ設定.
 * 
 * @author Junki Yamada
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecuritySetting extends WebSecurityConfigurerAdapter {

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(final WebSecurity web) {

        web.ignoring().antMatchers("/css/**", "/lib/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers(PagePaths.Login.PATH).permitAll().anyRequest().authenticated();
    }
}
