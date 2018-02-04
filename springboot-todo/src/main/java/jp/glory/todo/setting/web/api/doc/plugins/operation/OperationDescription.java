package jp.glory.todo.setting.web.api.doc.plugins.operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.glory.todo.context.base.web.api.OriginalOperationDoc;

/**
 * オペレーションの説明.
 * @author Junki Yamada
 *
 */
public class OperationDescription {

    /**
     * 名前.
     */
    private final String name;

    /**
     * 概要.
     */
    private final String[] summary;

    /**
     * 事前条件.
     */
    private final String[] preconditions;

    /**
     * 事後条件.
     */
    private final String[] postcondition;

    /**
     * コンストラクタ.
     * @param doc ドキュメント
     */
    OperationDescription(final OriginalOperationDoc doc) {

        this.name = doc.name();
        this.summary = doc.summary();

        final List<String> preconditionList = new ArrayList<>();
        if (doc.ignoreAuth() == false) {

            preconditionList.add("ログインしていること");
        }
        preconditionList.addAll(Arrays.asList(doc.preconditions()));

        this.preconditions = preconditionList.toArray(new String[]{});

        this.postcondition = doc.postcondition();
    }

    /**
     * 事前条件を持っているかを判定する.
     * @return 持っている場合：true、持っていない場合：false
     */
    boolean hasPrecondtions() {

        return 0 < preconditions.length;
    }

    /**
     * @return the name
     */
    String getName() {
        return name;
    }

    /**
     * @return the summary
     */
    String[] getSummary() {
        return summary;
    }

    /**
     * @return the preconditions
     */
    String[] getPreconditions() {
        return preconditions;
    }

    /**
     * @return the postcondition
     */
    String[] getPostcondition() {
        return postcondition;
    }
}
