package jp.glory.framework.web.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jp.glory.framework.web.exception.InvalidRequestException;
import jp.glory.framework.web.exception.handler.response.InvalidErrorResponse;

/**
 * 例外ハンドリング.
 * @author Junki Yamada
 *
 */
@ControllerAdvice
public class WebapplicationExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * リクエスト不正例外のハンドラ<br>
     * エラーメッセージを生成して、Bad Requestで返す。
     * @param ex 例外
     * @return 入力不正のレスポンス
     */
    @ExceptionHandler(InvalidRequestException.class)
    protected ResponseEntity<Object> handleInvalidRequestException(final InvalidRequestException ex) {

        final InvalidErrorResponse response = new InvalidErrorResponse();

        ex.getErrors().toList().stream()
            .map(v -> v.getMessage())
            .forEach(response.getErrors()::add);

        return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
    }
}
