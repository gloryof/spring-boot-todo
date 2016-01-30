package jp.glory.domain.common.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 最大文字数.<br>
 * 1以上を指定する。<br>
 * 0以下を指定した場合は最大文字数を設定しない。
 * @author Junki Yamada
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface MaxLength {

    /**
     * 値の設定.
     * @return 最大文字数
     */
    int value() default 0;

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
