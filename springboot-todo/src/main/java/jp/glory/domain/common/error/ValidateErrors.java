package jp.glory.domain.common.error;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * 入力チェックエラーリスト.
 * @author Junki Yamada
 */
public class ValidateErrors {

    /** エラーリスト. */
    private final List<ValidateError> errors = new ArrayList<>();

    /**
     * エラーリストを返却する.<br>
     * 返却されるリストは変更不能。
     * @return エラーリスト
     */
    public List<ValidateError> toList() {

        return Collections.unmodifiableList(errors);
    }

    /**
     * エラーを追加する.
     * @param error エラー情報
     */
    public void add(final ValidateError error) {

        final Predicate<ValidateError> hasError =
                v -> v.getMessage().equals(error.getMessage());

        if (errors.stream().anyMatch(hasError)) {

            return;
        }

        errors.add(error);
    }

    /**
     * エラー情報の全てのエラーを追加する
     * @param paramErrors エラー情報リスト
     */
    public void addAll(final ValidateErrors paramErrors) {

        paramErrors.toList().forEach(this::add);
    }

    /**
     * エラーがあるか判定する.
     * @return エラーがある場合：true、エラーがない場合：false
     */
    public boolean hasError() {

        return !errors.isEmpty();
    }
}
