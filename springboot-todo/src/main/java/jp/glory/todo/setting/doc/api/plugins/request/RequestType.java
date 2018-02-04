package jp.glory.todo.setting.doc.api.plugins.request;

import org.springframework.web.bind.annotation.PathVariable;

import springfox.documentation.service.ResolvedMethodParameter;

/**
 * リクエストタイプ.
 * @author Junki Yamada
 *
 */
enum RequestType {

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
    static RequestType typeOf(final ResolvedMethodParameter param) {

        if (param.hasParameterAnnotation(PathVariable.class)) {

            return Path;
        }
        return Query;
    }
}
