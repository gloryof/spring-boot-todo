package jp.glory.domain.todo.value;

import jp.glory.domain.common.type.EntityId;

/**
 * TODOのID.
 * 
 * @author Junki Yamada
 *
 */
public class TodoId extends EntityId {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = -2754585214392061704L;

    /**
     * コンストラクタ.
     * 
     * @param paramValue
     *            値
     */
    public TodoId(final Long paramValue) {

        super(paramValue);
    }

    /**
     * 未採番のTODO-IDを取得する.
     * 
     * @return 未採番のTODO-ID
     */
    public static TodoId notNumberingValue() {

        return new TodoId(null);
    }
}
