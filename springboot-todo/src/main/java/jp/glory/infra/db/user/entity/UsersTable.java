package jp.glory.infra.db.user.entity;

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
    private long userId = 0l;

    /**
     * ログインID.
     */
    @Getter
    @Setter
    private String loginId = null;

    /**
     * ユーザ名.
     */
    @Getter
    @Setter
    private String userName = null;

    /**
     * パスワード.
     */
    @Getter
    @Setter
    private String password = null;
}
