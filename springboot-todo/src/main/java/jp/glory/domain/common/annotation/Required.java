package jp.glory.domain.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 必須入力設定アノテーション.
 * @author Junki Yamada
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Required {
    
    /**
     * ラベルの設定.
     * @return  ラベル
     */
    String label() default "";

    /**
     * 有効フラグ.
     * @return 有効な場合：true、無効な場合：false
     */
    boolean isActive() default true;
}