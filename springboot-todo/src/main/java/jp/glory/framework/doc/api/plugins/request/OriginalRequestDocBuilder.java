package jp.glory.framework.doc.api.plugins.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.members.RawField;

import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResolvedMethodParameter;

/**
 * リクエストの独自ドキュメントビルダー.
 * @author Junki Yamada
 */
class OriginalRequestDocBuilder {

    /**
     * パラメタリスト.
     */
    private final List<ResolvedMethodParameter> methodParams;
    
    /**
     * コンストラクタ.
     * @param methodParams メソッドパラメータリスト
     */
    OriginalRequestDocBuilder(final List<ResolvedMethodParameter> methodParams) {

        this.methodParams = methodParams;
    }

    /**
     * ビルド.
     * @return 対象パラメータリスト
     */
    List<Parameter> build() {

        return methodParams.stream()
                           .flatMap(v -> convertTargetParmaeters(v).stream())
                           .sorted()
                           .map(v -> v.toSpec())
                           .collect(Collectors.toList());
    }

    /**
     * 対象パラメータリストに変換する.
     * @param methodParam メソッドパラメータ
     * @return 対象パラメータリスト
     */
    private List<TargetParameter> convertTargetParmaeters(final ResolvedMethodParameter methodParam) {

        final ResolvedType paramType = methodParam.getParameterType();

        if (paramType.isPrimitive() || paramType.isInstanceOf(String.class)) {

            final List<TargetParameter> returnList = new ArrayList<>();
            createPrimitiveParameter(methodParam).ifPresent(returnList::add);

            return returnList;
        }

        return createBeanParameters(methodParam);
    }

    /**
     * プリミティブ型とStringの場合のパラメータ情報を作成する.
     * @param methodParam メソッドパラメータ
     * @return パラメータ情報
     */
    private Optional<TargetParameter> createPrimitiveParameter(final ResolvedMethodParameter methodParam) {

        if (!methodParam.hasParameterAnnotation(OriginalRequestlDoc.class)) {

            return Optional.empty();
        }

        final Optional<OriginalRequestlDoc> optDoc = Optional
                .ofNullable(methodParam.findAnnotation(OriginalRequestlDoc.class).orNull());

        final ParameterDescription description = new ParameterDescription(methodParam, optDoc);
        final ParameterRestriction restriction = new ParameterRestriction(optDoc);

        return Optional.of(new TargetParameter(description, restriction));
    }

    /**
     * Beanのパラメータリストを作成する.
     * @param methodParam メソッドパラメータ
     * @return パラメータリスト
     */
    private List<TargetParameter> createBeanParameters(final ResolvedMethodParameter methodParam) {

        final ResolvedType paramType = methodParam.getParameterType();
        final List<RawField> fields = paramType.getMemberFields();

        return IntStream.range(0, fields.size())
                        .mapToObj(v -> this.convertTuple(v + 1, fields.get(v)))
                        .filter(v -> v.optRequestDoc.isPresent())
                        .map(v -> new TargetParameter(v.description, new ParameterRestriction(v.optRequestDoc)))
                        .collect(Collectors.toList());
    }

    /**
     * フィールド情報を一時タプルに変換する.
     * @param field フィールド情報
     * @return 一時タプル
     */
    private TempTuple convertTuple(final int order, final RawField field) {

        final Optional<OriginalRequestlDoc> optRequestDoc= Arrays.stream(field.getAnnotations())
                                                                 .filter(v -> v instanceof OriginalRequestlDoc)
                                                                 .findAny()
                                                                 .map(v -> (OriginalRequestlDoc) v);

        final ParameterDescription description = new ParameterDescription(order ,field, optRequestDoc);

        return new TempTuple(description, optRequestDoc);
    }

    /**
     * 一時的に使うタプル.<br>
     * 標準APIで提供されればいらない。
     * @author Junki Yamada
     *
     */
    private class TempTuple {

        /**
         * パラメータの説明.
         */
        private final ParameterDescription description;

        /**
         * 対象アノテーション.
         */
        private final Optional<OriginalRequestlDoc> optRequestDoc;

        /**
         * コンストラクタ.
         * @param description パラメータの説明
         * @param optRequestDoc 対象アノテーション.
         */
        private TempTuple(final ParameterDescription description, final Optional<OriginalRequestlDoc> optRequestDoc) {

            this.description = description;
            this.optRequestDoc = optRequestDoc;
        }
    }
}
