package jp.glory.framework.doc.api.plugins.oeration;

import java.util.Arrays;

import jp.glory.framework.doc.api.annotation.OriginalOperationDoc;
import jp.glory.framework.doc.api.common.SummaryFormatter;
import springfox.documentation.builders.OperationBuilder;

public class OriginalOperationDocBuilder {

    /**
     * ドキュメント.
     */
    private final OriginalOperationDoc doc;

    /**
     * コンストラクタ.
     * @param context リクエストマッピングコンテキスト
     */
    public OriginalOperationDocBuilder(final OriginalOperationDoc doc) {

        this.doc = doc;
    }

    /**
     * ビルド.
     * @param builder オペレーションビルダー
     */
    public void build(final OperationBuilder builder) {

        final OperationDescription description = new OperationDescription(doc);

        builder.summary(description.getName());
        builder.notes(createNotes(description));
    }

    /**
     * ノートを作成する.
     * @param description オペレーションの説明
     * @return ノート
     */
    private String createNotes(final OperationDescription description) {

        final SummaryFormatter formatter = new SummaryFormatter();

        formatter.bold("[概要]").lineBreak();
        Arrays.stream(description.getSummary()).forEach(v -> formatter.text(v).lineBreak());
        formatter.newParagraph();

        formatter.bold("[事前条件]").lineBreak();
        if (description.hasPrecondtions()) {

            Arrays.stream(description.getPreconditions()).forEach(formatter::list);
        } else {

            formatter.list("なし");
        }
        formatter.newParagraph();

        formatter.bold("[事後条件]").lineBreak();
        Arrays.stream(description.getPostcondition()).forEach(formatter::list);
        formatter.newParagraph();

        return formatter.output();
    }
}
