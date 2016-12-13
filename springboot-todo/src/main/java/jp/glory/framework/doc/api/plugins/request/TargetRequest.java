package jp.glory.framework.doc.api.plugins.request;

import java.util.Arrays;

import jp.glory.framework.doc.api.common.SummaryFormatter;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.service.Parameter;

/**
 * 対象リクエスト
 * @author Junki Yamada
 *
 */
class TargetRequest implements Comparable<TargetRequest> {

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

        final SummaryFormatter formatter = new SummaryFormatter();

        formatter.text("[項目名]").lineBreak();
        formatter.text(description.getLabel()).lineBreak();
        formatter.newParagraph();

        createRestrictions(formatter);

        return formatter.output();
    }

    /**
     * 制限部分を生成する.
     * @param formatter フォーマッター
     */
    void createRestrictions(final SummaryFormatter formatter) {

        if (!restriction.isRestriction()) {

            return;
        }

        formatter.text("[制限]").lineBreak();

        if (restriction.isRequired()) {

            formatter.list("必須");
        }

        if (restriction.hasMaxLength()) {

            formatter.list(restriction.getMaxLength() + "文字以下");
        }

        Arrays.stream(restriction.getValidChars())
              .forEach(v -> formatter.list(v.label));

        Arrays.stream(restriction.getAddtionalRestrictions())
              .forEach(formatter::list);
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
