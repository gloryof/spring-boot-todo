package jp.glory.todo.context.user.domain.value;

import jp.glory.todo.context.base.domain.annotation.MaxLength;
import jp.glory.todo.context.base.domain.annotation.Required;
import jp.glory.todo.context.base.domain.type.InputText;

/**
 * ユーザ名.
 * 
 * @author Junki Yamada
 */
@Required(label = UserName.LABEL)
@MaxLength(value = 50, label = UserName.LABEL)
public class UserName extends InputText {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 8538281104376981045L;

    /** ラベル. */
    public static final String LABEL = "ユーザ名";

    /**
     * コンストラクタ.<br>
     * 値を設定する
     * 
     * @param paramValue
     *            値
     */
    public UserName(final String paramValue) {

        super(paramValue);
    }

    /**
     * 空の値オブジェクトを返却する.
     * 
     * @return ユーザ名
     */
    public static UserName empty() {

        return new UserName("");
    }
}