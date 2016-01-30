package jp.glory.domain.common.error;

import java.text.MessageFormat;
import java.util.Optional;

/**
 * 入力チェックエラー.
 * @author Junki Yamada
 */
public class ValidateError {

    /** エラー情報. */
    private final ErrorInfo errorInfo;

    /** メッセージパラメータ. */
    private final Object[] messageParam;

    /**
     * コンストラクタ.
     * @param paramErrorInfo エラー情報
     * @param paramMessageParam メッセージパラメータ
     */
    public ValidateError(final ErrorInfo paramErrorInfo, final Object... paramMessageParam) {

        errorInfo = paramErrorInfo;
        messageParam = paramMessageParam;
    }

    /**
     * メッセージを取得する.
     * @return  メッセージ
     */
    public String getMessage() {

        final String base = errorInfo.message;

        final MessageFormat format = new MessageFormat(base);
        return format.format(messageParam);
    }

    /**
     * エラー情報が同一か判定する.
     * @param paramError エラー情報 
     * @return 同一の場合：true、異なる場合：false
     */
    public boolean isSame(final ValidateError paramError) {

        final Optional<ValidateError> optionalValue =Optional.ofNullable(paramError);

        if (!optionalValue.isPresent()) {

            return false;
        }

        return this.getMessage().equals(paramError.getMessage());
    }
}