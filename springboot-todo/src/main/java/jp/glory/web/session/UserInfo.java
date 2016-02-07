package jp.glory.web.session;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import jp.glory.domain.user.entity.User;
import jp.glory.domain.user.value.UserId;
import jp.glory.domain.user.value.UserName;
import lombok.Getter;

/**
 * ユーザ情報セッション.
 * 
 * @author Junki Yamada
 *
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = WebApplicationContext.SCOPE_SESSION)
public class UserInfo {

    /**
     * ユーザID.
     */
    @Getter
    private UserId userId = null;

    /**
     * ユーザ名.
     */
    @Getter
    private UserName name = null;

    /**
     * 有効フラグ. <br>
     * 有効な場合：true、無効な場合：false
     */
    @Getter
    private boolean activate = false;

    /**
     * セッションの有効化.
     * 
     * @param user
     *            ユーザ情報
     */
    public void activate(final User user) {

        this.activate = true;
        this.userId = user.getUserId();
        this.name = user.getUserName();
    }

    /**
     * セッションの無効化.
     */
    public void inactivate() {

        this.activate = false;
        this.userId = null;
        this.name = null;
    }
}
