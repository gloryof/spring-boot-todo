package jp.glory.infra.db.user.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * usersテーブル.
 * @author Junki Yamada
 *
 */
@Entity
@Table(name = "users")
public class UsersTable {

    /**
     * ユーザId.
     */
    @Getter
    @Setter
    @Id
    @Column(name = "user_id")
    private long userId = 0l;

    /**
     * ログインID.
     */
    @Getter
    @Setter
    @Column(name = "login_id")
    private String loginId = null;

    /**
     * ユーザ名.
     */
    @Getter
    @Setter
    @Column(name = "user_name")
    private String userName = null;

    /**
     * パスワード.
     */
    @Getter
    @Setter
    private String password = null;
}
