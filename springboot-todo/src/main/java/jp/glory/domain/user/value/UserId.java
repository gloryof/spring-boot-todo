package jp.glory.domain.user.value;

import jp.glory.domain.common.type.EntityId;

/**
 * ユーザID.<br>
 * ユーザをユニークに識別するためのID。<br>
 * ログインするためのログインIDとは別。
 * 
 * @author Junki Yamada
 */
public class UserId extends EntityId {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 4747463306943376449L;

    /**
     * 値を設定する
     * 
     * @param paramValue
     *            値
     */
    public UserId(final Long paramValue) {

        super(paramValue);
    }

    /**
     * 未採番のユーザIDを取得する.
     * 
     * @return ユーザID
     */
    public static UserId notNumberingValue() {

        return new UserId(null);
    }
}