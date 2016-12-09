package jp.glory.framework.doc.api.plugins.request;

import java.util.Optional;

import com.fasterxml.classmate.members.RawField;

import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResolvedMethodParameter;

/**
 * パラメータの説明.
 * @author Junki Yamada
 *
 */
class ParameterDescription {

    /**
     * キー項目.
     */
    private final boolean key;

    /**
     * パラメータ名.
     */
    private final String parameterName;

    /**
     * パラメータタイプ.
     */
    private final ParameterType type;

    /**
     * パラメータのクラス.
     */
    private Class<?> parameterClass;

    /**
     * ラベル.
     */
    private final String label;
    /**
     * 並び順.
     */
    private final int order;

    /**
     * コンストラクタ.
     * @param param メソッドパラメータ
     * @param optDoc ドキュメントアノテーション
     */
    ParameterDescription(final ResolvedMethodParameter param, final Optional<OriginalRequestlDoc> optDoc) {

        this.key = optDoc.map(v -> v.key()).orElse(false);
        this.parameterName = Optional.ofNullable(param.defaultName().orNull())
                .orElseThrow(() -> new IllegalStateException("parameterName is required."));
        this.type = ParameterType.typeOf(param);
        this.parameterClass = param.getParameterType().getErasedType();
        this.label = optDoc.map(v -> v.name()).orElse("");
        this.order = param.getParameterIndex() + 1;
    }

    /**
     * コンストラクタ.
     * @param order 並び順
     * @param field フィールド情報
     * @param optDoc ドキュメントアノテーション
     */
    public ParameterDescription(final int order ,final RawField field, final Optional<OriginalRequestlDoc> optDoc) {

        this.key = optDoc.map(v -> v.key()).orElse(false);
        this.parameterName = field.getName();
        this.type = ParameterType.Body;
        this.parameterClass = field.getRawMember().getType();
        this.label = optDoc.map(v -> v.name()).orElse("");
        this.order = order;
    }

    /**
     * @return the key
     */
    boolean isKey() {
        return key;
    }

    /**
     * @return the parameterClass
     */
    Class<?> getParameterClass() {
        return parameterClass;
    }

    /**
     * @param parameterClass the parameterClass to set
     */
    void setParameterClass(Class<?> parameterClass) {
        this.parameterClass = parameterClass;
    }

    /**
     * @return the parameterName
     */
    String getParameterName() {
        return parameterName;
    }

    /**
     * @return the type
     */
    ParameterType getType() {
        return type;
    }

    /**
     * @return the label
     */
    String getLabel() {
        return label;
    }

    /**
     * モデル情報を取得する.
     * @return モデル情報
     */
    ModelRef getModelRef() {

        return new ModelRef(parameterClass.getSimpleName().toLowerCase());
    }

    /**
     * @return the order
     */
    int getOrder() {
        return order;
    }
}
