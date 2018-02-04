package jp.glory.todo.context.base.domain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.glory.todo.context.base.domain.annotation.param.ValidCharcterType;
/**
 * 許可文字列.<br>
 * 正規表現で指定する。<br>
 * 複数指定した場合は全てOR条件となる。
 * @author Junki Yamada
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface ValidCharacters {

    /**
     * 許可文字列のタイプを指定する.
     * @return 許可文字列タイプ
     */
    ValidCharcterType[] value() default {};

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