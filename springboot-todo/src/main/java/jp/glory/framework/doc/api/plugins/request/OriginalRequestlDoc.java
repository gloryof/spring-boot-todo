package jp.glory.framework.doc.api.plugins.request;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.glory.domain.common.type.Validatable;

/**
 * リクエストの独自ドキュメントアノテーション.
 * @author Junki Yamada
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface OriginalRequestlDoc {

    /**
     * 名前.
     * @return 名前
     */
    String name();

    /**
     * キー項目であるかのフラグ.
     * @return フラグ
     */
    boolean key() default false;

    /**
     * 必須フラグ.<br>
     * 基本的にはドメインオブジェクト入力制約に準拠させるべきだが、<br>
     * プリミティブ型などの場合は設定ができた否めこのフラグを使用する.
     */
    boolean requied() default false;

    /**
     * 入力チェックタイプ.
     * @return タイプ
     */
    Class<? extends Validatable> validateType() default Validatable.class; 

    /**
     * タイプ以外の制限（e.g.相関チェック）
     * @return　制限の内容を説明した配列
     */
    String[] additionalRestrictions() default {};
}