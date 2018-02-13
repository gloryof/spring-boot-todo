package jp.glory.todo.context.base.web;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jp.glory.todo.context.user.domain.entity.RegisteredUser;
import jp.glory.todo.context.user.domain.value.LoginId;
import jp.glory.todo.context.user.domain.value.Password;
import jp.glory.todo.context.user.domain.value.UserId;
import jp.glory.todo.context.user.domain.value.UserName;
import lombok.Getter;

/**
 * ユーザ情報セッション.
 * 
 * @author Junki Yamada
 *
 */
public class UserInfo implements UserDetails {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = -8274405920991317550L;

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
     * ログインID
     */
    @Getter
    private LoginId loginId = null;

    /**
     * パスワード.
     */
    private Password password = null;


    /**
     * コンストラクタ.
     * @param user ユーザ
     */
    public UserInfo(final RegisteredUser user) {

        this.userId = user.getUserId();
        this.name = user.getUserName();
        this.loginId = user.getLoginId();
        this.password = user.getPassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Arrays.asList(new GrantedAuthority[] { new SimpleGrantedAuthority("USER") });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPassword() {

        return password.getValue();
    }

    /**
     * ログインIDを返す.<br>
     * Spring Security上では認証情報のキーとなるものをusernameとしているため、<br>
     * ログインIDを紐付けている。
     * {@link UserDetails#getUsername()}を参照。
     */
    @Override
    public String getUsername() {

        return loginId.getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}
