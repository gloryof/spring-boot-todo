package jp.glory.domain.common.type;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

import jp.glory.domain.common.annotation.MaxLength;
import jp.glory.domain.common.annotation.Required;
import jp.glory.domain.common.annotation.ValidCharacters;
import jp.glory.domain.common.annotation.param.ValidCharcterType;
import jp.glory.domain.common.error.ErrorInfo;
import jp.glory.domain.common.error.ValidateError;
import jp.glory.domain.common.error.ValidateErrors;
import lombok.Getter;


/**
 * 入力テキスト.
 * @author Junki Yamada
 */
@MaxLength(isActive = false)
@ValidCharacters(isActive = false)
@Required(isActive = false)
public abstract class InputText implements Validatable, Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 1591293540244535848L;

    /**
     * 値.
     */
    @Getter
    private final String value;

    /** 必須入力設定. */
    private final Required requiredSetting;

    /** 最大文字数設定. */
    private final MaxLength maxLengthSetting;

    /** 有効文字列設定. */
    private final ValidCharacters validCharSetting;

    /**
     * コンストラクタ.
     * 
     * @param paramValue 値
     */
    protected InputText(final String paramValue) {

        value = Optional.ofNullable(paramValue).orElse("");
        requiredSetting = this.getClass().getAnnotation(Required.class);
        maxLengthSetting  = this.getClass().getAnnotation(MaxLength.class);
        validCharSetting = this.getClass().getAnnotation(ValidCharacters.class);
    }

    /**
     * 入力情報の検証を行う.
     * @return 検証結果
     */
    @Override
    public ValidateErrors validate() {

        final ValidateErrors errors = new ValidateErrors();

        if (isRequiredError()) {

            errors.add(new ValidateError(ErrorInfo.Required, requiredSetting.label()));
        }

        if (isMaxLengthError()) {

            final Object[] messageParam = { maxLengthSetting.label(), maxLengthSetting.value() };
            errors.add(new ValidateError(ErrorInfo.MaxLengthOver, messageParam));
        }

        if (isValidCharcterError()) {

            errors.add(new ValidateError(ErrorInfo.InvalidCharacters, validCharSetting.label()));
        }

        return errors;
    }

    /**
     * 必須入力チェックエラーかを判定する.
     * @return エラーがある場合：true、エラーがない場合：false
     */
    private boolean isRequiredError() {

        if (!requiredSetting.isActive()) {

            return false;
        }

        return value.isEmpty();
    }

    /**
     * 最大文字数エラーかを判定する.
     * @return エラーがある場合：true、エラーがない場合：false
     */
    private boolean isMaxLengthError() {

        if (!maxLengthSetting.isActive()) {

            return false;
        }

        return (maxLengthSetting.value()< value.length());
    }

    /**
     * 許可文字列エラーがあるかを判定する.
     * @return エラーがある場合：true、エラーがない場合：false
     */
    private boolean isValidCharcterError() {

        if (!validCharSetting.isActive()) {

            return false;
        }

        if (value.isEmpty()) {

            return false;
        }

        final Optional<ValidCharcterType> matchedPattern
                = Arrays.stream(validCharSetting.value()).filter(v -> !v.isMatch(value)).findFirst();
        return matchedPattern.isPresent();
    }
}