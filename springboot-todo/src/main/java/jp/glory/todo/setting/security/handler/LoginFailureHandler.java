package jp.glory.todo.setting.security.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * ログイン失敗時のハンドラ.
 * @author Junki Yamada
 *
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {

    /**
     * 遷移先URL.
     */
    private final String redirectUrl;

    private final DefaultRedirectStrategy strategy = new DefaultRedirectStrategy();

    /**
     * コンストラクタ.
     * @param redirectUrl 遷移先URL
     */
    public LoginFailureHandler(final String redirectUrl) {

        this.redirectUrl = redirectUrl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
            final AuthenticationException exception) throws IOException, ServletException {

        request.getSession(true).setAttribute("failureMessages", createMessages(exception));
        strategy.sendRedirect(request, response, redirectUrl);
    }

    /**
     * メッセージを作成する.
     * @param exception 例外
     * @return メッセージリスト
     */
    private List<String> createMessages(final AuthenticationException exception) {

        final String message;

        if (exception instanceof BadCredentialsException) {

            message = "認証に失敗しました";
        } else {

            message = "原因不明のエラーが発生しました";
        }

        final List<String> results = new ArrayList<>();

        results.add(message);

        return results;
    }
}
