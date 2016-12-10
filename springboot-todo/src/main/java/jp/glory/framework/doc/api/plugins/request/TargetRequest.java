package jp.glory.framework.doc.api.plugins.request;

import java.util.Arrays;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.service.Parameter;

/**
 * 対象リクエスト
 * @author Junki Yamada
 *
 */
class TargetRequest implements Comparable<TargetRequest> {

    /**
     * 改行文字.
     */
    private static final String SEP = "\r\n";

    /**
     * パラメータの説明.
     */
    private final RequestDescription description;

    /**
     * 制約.
     */
    private final RequestRestriction restriction;

    /**
     * コンストラクタ.
     * @param description 説明
     * @param restriction 制約
     */
    TargetRequest(final RequestDescription description, final RequestRestriction restriction) {

        this.description = description;
        this.restriction = restriction;
    }

    /**
     * 仕様のドキュメントを作成する.
     * @return 仕様のドキュメント
     */
    public Parameter toSpec() {

        final ParameterBuilder builder = new ParameterBuilder();

        builder.name(description.getParameterName());
        builder.modelRef(description.getModelRef());
        builder.parameterType(description.getType().value());
        builder.description(createDescription());
        builder.required(restriction.isRequired());

        return builder.build();
    }

    /**
     * 説明部分を作成する.
     * @return 説明部分の文字列
     */
    private String createDescription() {

        final StringBuilder builder = new StringBuilder();

        builder.append("[項目名]  " + SEP);
        builder.append(description.getLabel() + SEP);
        builder.append(SEP);

        builder.append(createRestrictions());

        return builder.toString();
    }

    /**
     * 制限部分を生成する.
     * @return 制限部分の文字列
     */
    String createRestrictions() {

        final StringBuilder builder = new StringBuilder();

        if (!restriction.isRestriction()) {

            return builder.toString();
        }

        builder.append("[制限]" + SEP);

        if (restriction.isRequired()) {

            builder.append("- 必須" + SEP);
        }

        if (restriction.hasMaxLength()) {

            builder.append("- " + restriction.getMaxLength() + "文字以下" + SEP);
        }

        Arrays.stream(restriction.getValidChars())
              .forEach(v -> builder.append("- " + v.label + SEP));

        Arrays.stream(restriction.getAddtionalRestrictions())
              .forEach(v -> builder.append("- " + v + SEP));

        return builder.toString();
    }

    @Override
    public int compareTo(final TargetRequest o) {

        if (o == null) {

            return -1;
        }

        if (this.description.isKey() == true &&
                o.description.isKey() == false) {

            return -1;
        }

        if (this.description.isKey() == false &&
                o.description.isKey() == true) {

            return 1;
        }

        final int typeOrder = this.description.getType().compareTo(o.description.getType());

        if (typeOrder != 0) {

            return typeOrder;
        }

        return this.description.getOrder() - o.description.getOrder();
    }

}
