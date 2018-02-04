package jp.glory.todo.context.todo.domain.value;

import jp.glory.todo.context.base.domain.annotation.MaxLength;
import jp.glory.todo.context.base.domain.annotation.Required;
import jp.glory.todo.context.base.domain.type.InputText;

/**
 * TODOの概要.
 * 
 * @author Junki Yamada
 *
 */
@Required(label = Summary.LABEL)
@MaxLength(value = 20, label = Summary.LABEL)
public class Summary extends InputText {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = -1746975120830986730L;

    /** ラベル. */
    public static final String LABEL = "概要";

    /**
     * コンストラクタ.<br>
     * 値を設定する
     * 
     * @param paramValue
     *            値
     */
    public Summary(final String paramValue) {

        super(paramValue);
    }

    /**
     * 空の値オブジェクトを返却する.
     * 
     * @return 概要
     */
    public static Summary empty() {

        return new Summary("");
    }
}
