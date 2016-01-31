package jp.glory.domain.user.value;

import jp.glory.domain.common.annotation.MaxLength;
import jp.glory.domain.common.annotation.Required;
import jp.glory.domain.common.type.InputText;

/**
 * ユーザ名.
 * 
 * @author Junki Yamada
 */
@Required(label = UserName.LABEL)
@MaxLength(value = 50, label = UserName.LABEL)
public class UserName extends InputText {

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
     * @return ログインID
     */
    public static UserName empty() {

        return new UserName("");
    }
}