package jp.glory.framework.doc.api.common;

/**
 * 文章のフォーマットを行う.<br>
 * GitHub Flavored Markdown(GFM)のフォーマットに変換する.
 * @author Junki Yamada
 *
 */
public class SummaryFormatter {

    /**
     * 改行文字.
     */
    private static final String SEP = "\r\n";

    /**
     * 行末.
     */
    private static final String LINE_END = "  " + SEP;

    /**
     * ビルダー.
     */
    private final StringBuilder builder = new StringBuilder();

    /**
     * 文章として追加する.
     * @param value 文字列
     */
    public void paragraph(final String value) {

        builder.append(value + LINE_END);
    }

    /**
     * 空行を入れる.
     */
    public void insertEmptyRow() {

        builder.append(LINE_END);
    }

    /**
     * リストとしてする.
     * @param value
     */
    public void list(final String value) {

        builder.append("- " + value + SEP);
    }

    /**
     * 文字列として出力する.
     * @return
     */
    public String output() {

        return builder.toString();
    }
}
