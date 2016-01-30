package jp.glory.domain.common.annotation.param;

import java.util.Optional;

/**
 * 許可文字列タイプ
 * @author Junki Yamada
 */
public enum ValidCharcterType {

    /** 半角文字のみ */
    OnlySingleByteChars("[ -~｡-ﾟ]+");

    /** パターン */
    private final String pattern;

    /**
     * コンストラクタ
     * @param paramPattern パターン値 
     */
    private ValidCharcterType(final String paramPattern) {

        pattern = paramPattern;
    }

    public boolean isMatch(final String value) {

        Optional<String> optionalValue = Optional.ofNullable(value);

        return optionalValue.orElse("").matches(pattern);
    }
}