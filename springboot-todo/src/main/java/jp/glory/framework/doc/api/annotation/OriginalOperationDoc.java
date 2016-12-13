package jp.glory.framework.doc.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * オペレーションの独自ドキュメントアノテーション.
 * @author Junki Yamada
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface OriginalOperationDoc {

    /**
     * 名前.
     * @return 名前
     */
    String name();

    /**
     * 概要.
     * @return 概要
     */
    String[] summary();

    /**
     * 認証不要フラグ.
     * @return 認証不要フラグ
     */
    boolean ignoreAuth() default false;

    /**
     * 事前条件リスト.
     * @return 事前条件リスト
     */
    String[] preconditions() default {};

    /**
     * 事後条件リスト.
     * @return 事後条件リスト
     * @return
     */
    String[] postcondition();
}
