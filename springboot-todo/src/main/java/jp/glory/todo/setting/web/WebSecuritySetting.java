package jp.glory.todo.setting.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jp.glory.todo.context.base.web.api.ApiPaths;
import jp.glory.todo.context.base.web.page.PagePaths;
import jp.glory.todo.setting.security.handler.LoginFailureHandler;

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

        web.ignoring().antMatchers("/css/**", "/libs/**");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .antMatchers(PagePaths.Login.PATH, PagePaths.Join.PATH, ApiPaths.Account.PATH).permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage(PagePaths.Login.PATH)
                .loginProcessingUrl("/executeLogin")
                .failureHandler(createFailureHandler())
                .defaultSuccessUrl(PagePaths.Todo.PATH)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .invalidateHttpSession(true)
                .permitAll();
    }

    /**
     * ログイン失敗ハンドラを作成する.
     * @return ハンドラ
     */
    private AuthenticationFailureHandler createFailureHandler() {

        return new LoginFailureHandler(PagePaths.Login.PATH + "?error");
    }
}
