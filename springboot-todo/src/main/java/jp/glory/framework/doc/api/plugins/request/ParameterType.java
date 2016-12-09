package jp.glory.framework.doc.api.plugins.request;

import org.springframework.web.bind.annotation.PathVariable;

import springfox.documentation.service.ResolvedMethodParameter;

/**
 * パラメータタイプ.
 * @author Junki Yamada
 *
 */
enum ParameterType {

    Query,
    Header,
    Path,
    FormData,
    Body;


    /**
     * 値を取得する.<br>
     * 名称の小文字で返す。
     * @return 値
     */
    String value() {

        final String name = this.name();
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    /**
     * パラメータの設定内容からタイプを取得する.
     * @param param パラメータ内容
     * @return タイプ
     */
    static ParameterType typeOf(final ResolvedMethodParameter param) {

        if (param.hasParameterAnnotation(PathVariable.class)) {

            return Path;
        }
        return Query;
    }
}
