package jp.glory.domain.todo.value;

import jp.glory.domain.common.annotation.MaxLength;
import jp.glory.domain.common.type.InputText;

/**
 * メモ.
 * 
 * @author Junki Yamada
 *
 */
@MaxLength(value = 1000, label = Memo.LABEL)
public class Memo extends InputText {

    /** ラベル. */
    public static final String LABEL = "メモ";

    /**
     * コンストラクタ.<br>
     * 値を設定する
     * 
     * @param paramValue
     *            値
     */
    public Memo(final String paramValue) {

        super(paramValue);
    }

    /**
     * 空の値オブジェクトを返却する.
     * 
     * @return メモ
     */
    public static Memo empty() {

        return new Memo("");
    }
}
