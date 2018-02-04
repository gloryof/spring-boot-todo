package jp.glory.todo.setting.doc.api.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章のフォーマットを行う.<br>
 * GitHub Flavored Markdown(GFM)のフォーマットに変換する.
 * @author Junki Yamada
 *
 */
public class SummaryFormatter {
    
    /**
     * 文章のリスト.
     */
    private final List<Sentence> sentenceList = new ArrayList<>();

    /**
     * 通常のテキストとして追加する.
     * @param value 値
     * @return 
     */
    public Sentence text(final String value) {

        final Sentence sentence = new Sentence(value);
        sentenceList.add(sentence);

        return sentence;
    }

    /**
     * 空行を入れる.
     */
    public void newParagraph() {

        final Sentence sentence = new Sentence("").lineBreak();

        sentenceList.add(sentence);
    }

    /**
     * リストとして追加する.
     * @param value 文字列
     */
    public Sentence list(final String value) {

        final Sentence sentence = new Sentence(value).list();
        sentenceList.add(sentence);

        return sentence;
    }

    /**
     * 太文字にする.
     * @param value 文字列
     */
    public Sentence bold(final String value) {

        final Sentence sentence = new Sentence(value).bold();
        sentenceList.add(sentence);

        return sentence;
    }

    /**
     * 文字列として出力する.
     * @return  文字列
     */
    public String output() {

        final StringBuilder builder = new StringBuilder();

        this.sentenceList.forEach(v -> builder.append(v.output()));

        return builder.toString();
    }
}
