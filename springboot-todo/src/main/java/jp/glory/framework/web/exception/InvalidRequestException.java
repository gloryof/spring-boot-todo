package jp.glory.framework.web.exception;

import java.util.stream.Collectors;

import jp.glory.domain.common.error.ValidateErrors;
import lombok.Getter;

/**
 * WebAPIでのリクエスト不正例外.<br>
 * 入力チェックで不正な値になった場合にスローする。
 * 
 * @author Junki Yamada
 *
 */
public class InvalidRequestException extends RuntimeException {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = -4130281124432403624L;
    /**
     * エラー情報.
     */
    @Getter
    private final ValidateErrors errors;

    /**
     * コンストラクタ.
     * @param errors エラー情報
     */
    public InvalidRequestException(final ValidateErrors errors) {

        super();
        this.errors = errors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage() {

        return errors.toList().stream()
            .map(v -> v.getMessage())
            .collect(Collectors.joining(System.getProperty("line.separator")));
    }
}
