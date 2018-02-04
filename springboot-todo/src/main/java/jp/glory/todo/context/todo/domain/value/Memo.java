package jp.glory.todo.context.todo.domain.value;

import jp.glory.todo.context.base.domain.annotation.MaxLength;
import jp.glory.todo.context.base.domain.type.InputText;

/**
 * メモ.
 * 
 * @author Junki Yamada
 *
 */
@MaxLength(value = 1000, label = Memo.LABEL)
public class Memo extends InputText {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 9089718032072713273L;

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
