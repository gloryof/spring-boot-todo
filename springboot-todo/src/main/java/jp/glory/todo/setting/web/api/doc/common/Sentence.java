package jp.glory.todo.setting.web.api.doc.common;

/**
 * フォーマットされる文の一つの塊.
 * @author Junki Yamada
 *
 */
public class Sentence {

    /**
     * 改行文字.
     */
    private static final String SEP = "\r\n";

    /**
     * 行末.
     */
    private static final String LINE_END = "  " + SEP;


    /**
     * ベースとなる文字列.
     */
    private final String value;

    /**
     * 改行フラグ.
     */
    private boolean isLineBreak = false;

    /**
     * 太文字フラグ.
     */
    private boolean isBold = false;

    /**
     * リストフラグ.
     */
    private boolean isList = false;

    /**
     * コンストラクタ.
     * @param value ベースとなる文字列
     */
    Sentence(final String value) {

        this.value = value;
    }

    /**
     * 改行を入れる.
     * @return このオブジェクト
     */
    public Sentence lineBreak() {

        this.isLineBreak = true;

        return this;
    }

    /**
     * 太文字として設定する.
     * @return このオブジェクト
     */
    public Sentence bold() {

        this.isBold = true;

        return this;
    }

    /**
     * リストとして設定する.
     * @return このオブジェクト
     */
    public Sentence list() {

        this.isList = true;

        return this;
    }

    /**
     * 文字列として出力する.
     * @return 文字列
     */
    public String output() {

        final StringBuilder builder = new StringBuilder();
        builder.append(value);

        if (isBold) {

            builder.insert(0, "**");
            builder.append("**");
        }

        if (isList) {

            builder.insert(0, "- ");
            builder.append(SEP);
        }

        if (isLineBreak) {

            builder.append(LINE_END);
        }

        return builder.toString();
    }
}
