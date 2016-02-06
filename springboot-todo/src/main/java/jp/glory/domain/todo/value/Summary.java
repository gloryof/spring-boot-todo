package jp.glory.domain.todo.value;

import jp.glory.domain.common.annotation.MaxLength;
import jp.glory.domain.common.annotation.Required;
import jp.glory.domain.common.type.InputText;

/**
 * TODOの概要.
 * 
 * @author Junki Yamada
 *
 */
@Required(label = Summary.LABEL)
@MaxLength(value = 20, label = Summary.LABEL)
public class Summary extends InputText {

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
