package jp.glory.domain.common.annotation.param;

import java.util.Optional;

/**
 * 許可文字列タイプ
 * @author Junki Yamada
 */
public enum ValidCharcterType {

    /** 半角文字のみ */
    OnlySingleByteChars("[ -~｡-ﾟ]+", "半角文字のみ");


    /** ラベル. */
    public final String label;

    /** パターン */
    private final String pattern;

    /**
     * コンストラクタ
     * @param paramPattern パターン値 
     * @param paramLabel ラベル
     */
    private ValidCharcterType(final String paramPattern, final String paramLabel) {

        pattern = paramPattern;
        label = paramLabel;
    }

    public boolean isMatch(final String value) {

        Optional<String> optionalValue = Optional.ofNullable(value);

        return optionalValue.orElse("").matches(pattern);
    }
}