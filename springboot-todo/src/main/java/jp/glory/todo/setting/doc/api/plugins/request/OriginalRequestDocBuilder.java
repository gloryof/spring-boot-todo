package jp.glory.todo.setting.doc.api.plugins.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.members.RawField;

import jp.glory.todo.context.base.web.api.OriginalRequestlDoc;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResolvedMethodParameter;

/**
 * リクエストの独自ドキュメントビルダー.
 * @author Junki Yamada
 */
public class OriginalRequestDocBuilder {

    /**
     * パラメタリスト.
     */
    private final List<ResolvedMethodParameter> methodParams;
    
    /**
     * コンストラクタ.
     * @param methodParams メソッドパラメータリスト
     */
    public OriginalRequestDocBuilder(final List<ResolvedMethodParameter> methodParams) {

        this.methodParams = methodParams;
    }

    /**
     * ビルド.
     * @param builder オペレーションビルダー
     */
    public void build(final OperationBuilder builder) {

        final List<Parameter> parameters =  methodParams.stream()
                                                       .flatMap(v -> convertTargetParmaeters(v).stream())
                                                       .sorted()
                                                       .map(v -> v.toSpec())
                                                       .collect(Collectors.toList());

        builder.parameters(parameters);
    }

    /**
     * 対象パラメータリストに変換する.
     * @param methodParam メソッドパラメータ
     * @return 対象パラメータリスト
     */
    private List<TargetRequest> convertTargetParmaeters(final ResolvedMethodParameter methodParam) {

        final ResolvedType paramType = methodParam.getParameterType();

        if (paramType.isPrimitive() || paramType.isInstanceOf(String.class)) {

            final List<TargetRequest> returnList = new ArrayList<>();
            createPrimitiveRequest(methodParam).ifPresent(returnList::add);

            return returnList;
        }

        return createBeanRequests(methodParam);
    }

    /**
     * プリミティブ型とStringの場合のリクエスト情報を作成する.
     * @param methodParam メソッドパラメータ
     * @return リクエスト情報
     */
    private Optional<TargetRequest> createPrimitiveRequest(final ResolvedMethodParameter methodParam) {

        if (!methodParam.hasParameterAnnotation(OriginalRequestlDoc.class)) {

            return Optional.empty();
        }

        final Optional<OriginalRequestlDoc> optDoc = Optional
                .ofNullable(methodParam.findAnnotation(OriginalRequestlDoc.class).orNull());

        final RequestDescription description = new RequestDescription(methodParam, optDoc);
        final RequestRestriction restriction = new RequestRestriction(optDoc);

        return Optional.of(new TargetRequest(description, restriction));
    }

    /**
     * Beanのリクエストリストを作成する.
     * @param methodParam メソッドパラメータ
     * @return リクエストリスト
     */
    private List<TargetRequest> createBeanRequests(final ResolvedMethodParameter methodParam) {

        final ResolvedType paramType = methodParam.getParameterType();
        final List<RawField> fields = paramType.getMemberFields();

        return IntStream.range(0, fields.size())
                        .mapToObj(v -> this.convertTuple(v + 1, fields.get(v)))
                        .filter(v -> v.optRequestDoc.isPresent())
                        .map(v -> new TargetRequest(v.description, new RequestRestriction(v.optRequestDoc)))
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

        final RequestDescription description = new RequestDescription(order ,field, optRequestDoc);

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
        private final RequestDescription description;

        /**
         * 対象アノテーション.
         */
        private final Optional<OriginalRequestlDoc> optRequestDoc;

        /**
         * コンストラクタ.
         * @param description パラメータの説明
         * @param optRequestDoc 対象アノテーション.
         */
        private TempTuple(final RequestDescription description, final Optional<OriginalRequestlDoc> optRequestDoc) {

            this.description = description;
            this.optRequestDoc = optRequestDoc;
        }
    }
}
