package jp.glory.domain.user.value;

import jp.glory.domain.common.type.EntityId;

/**
 * ユーザID.<br>
 * ユーザをユニークに識別するためのID。<br>
 * ログインするためのログインIDとは別。
 * @author Junki Yamada
 */
public class UserId extends EntityId {

    /**
     * 値を設定する
     * @param paramValue 値 
     */
    public UserId(final Long paramValue) {

        super(paramValue);
    }
    /**
     * 未採番のレビューIDを取得する.
     * @return レビューID
     */
    public static UserId notNumberingValue() {

        return new UserId(null);
    }
}