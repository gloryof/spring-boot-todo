package jp.glory.todo.context.user.domain.value;

import jp.glory.todo.context.base.domain.annotation.MaxLength;
import jp.glory.todo.context.base.domain.annotation.Required;
import jp.glory.todo.context.base.domain.annotation.ValidCharacters;
import jp.glory.todo.context.base.domain.annotation.param.ValidCharcterType;
import jp.glory.todo.context.base.domain.type.InputText;

/**
 * ログインID.
 * 
 * @author Junki Yamada
 */
@Required(label = LoginId.LABEL)
@MaxLength(value = 50, label = LoginId.LABEL)
@ValidCharacters(value = ValidCharcterType.OnlySingleByteChars, label = LoginId.LABEL)
public class LoginId extends InputText {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = -4354665558755601414L;

    /** ラベル. */
    public static final String LABEL = "ログインID";

    /**
     * コンストラクタ.<br>
     * 値を設定する
     * 
     * @param paramValue
     *            値
     */
    public LoginId(final String paramValue) {

        super(paramValue);
    }

    /**
     * 空の値オブジェクトを返却する.
     * 
     * @return ログインID
     */
    public static LoginId empty() {

        return new LoginId("");
    }
}