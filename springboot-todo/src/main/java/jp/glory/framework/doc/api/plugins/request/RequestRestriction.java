package jp.glory.framework.doc.api.plugins.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jp.glory.domain.common.annotation.MaxLength;
import jp.glory.domain.common.annotation.Required;
import jp.glory.domain.common.annotation.ValidCharacters;
import jp.glory.domain.common.annotation.param.ValidCharcterType;
import jp.glory.domain.common.type.Validatable;
import jp.glory.framework.doc.api.annotation.OriginalRequestlDoc;

/**
 * リクエストの制約.
 * @author Junki Yamada
 *
 */
class RequestRestriction {

    /**
     * 制限の有無フラグ.
     */
    private final boolean restriction;

    /**
     * 追加の制限.
     */
    private final String[] addtionalRestrictions;

    /**
     * 必須フラグ.
     */
    private final boolean required;

    /**
     * 許可文字列タイプ.
     */
    private final ValidCharcterType[] validChars;

    /**
     * 最大文字数.
     */
    private final int maxLength;

    /**
     * コンストラクタ.
     * @param optDoc ドキュメントアノテーション
     */
    RequestRestriction(final Optional<OriginalRequestlDoc> optDoc) {

        final Class<? extends Validatable> validated;
        final List<Boolean> restrictionStatus = new ArrayList<>();
        final boolean isRequreid;
        if (optDoc.isPresent()) {

            final OriginalRequestlDoc doc = optDoc.get();
            this.addtionalRestrictions =  doc.additionalRestrictions();
            isRequreid = doc.key() || doc.requied();

            validated = doc.validateType(); 
        } else {

            this.addtionalRestrictions = new String[]{};
            isRequreid = false;

            validated = Validatable.class;
        }
        restrictionStatus.add(0 < addtionalRestrictions.length);

         
        final boolean domainRequired = Optional.ofNullable(validated.getAnnotation(Required.class))
                                               .map(v -> v.isActive())
                                               .orElse(false);
        this.required = (domainRequired || isRequreid);
        restrictionStatus.add(required);

        this.validChars = Optional.ofNullable(validated.getAnnotation(ValidCharacters.class))
                                  .filter(v -> v.isActive())
                                  .map(v -> v.value())
                                  .orElse(new ValidCharcterType[]{});
        restrictionStatus.add(0 < validChars.length);

        this.maxLength = Optional.ofNullable(validated.getAnnotation(MaxLength.class))
                                 .filter(v -> v.isActive())
                                 .map(v -> v.value())
                                 .orElse(0);
        restrictionStatus.add(0 < maxLength);

        this.restriction = restrictionStatus.contains(true);
    }

    /**
     * @return the restriction
     */
    boolean isRestriction() {
        return restriction;
    }

    /**
     * @return the addtionalRestrictions
     */
    String[] getAddtionalRestrictions() {
        return addtionalRestrictions;
    }

    /**
     * @return the required
     */
    boolean isRequired() {
        return required;
    }

    /**
     * @return the validChars
     */
    ValidCharcterType[] getValidChars() {
        return validChars;
    }

    /**
     * @return the maxLength
     */
    int getMaxLength() {
        return maxLength;
    }

    /**
     * 最大文字数の制限を持つかを判定する.
     * @return 制限を持つ場合：true、持たない場合：false
     */
    boolean hasMaxLength() {

        return 0 < maxLength;
    }

}
