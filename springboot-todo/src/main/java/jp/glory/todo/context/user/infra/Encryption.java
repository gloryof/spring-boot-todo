package jp.glory.todo.context.user.infra;

import jp.glory.todo.context.user.domain.value.Password;

/**
 * 暗号化インターフェイス.
 *
 * @author Junki Yamada
 */
public interface Encryption {

    /**
     * 暗号化処理.
     *
     * @param value
     *            値
     * @return パスワード
     */
    Password encrypt(final String value);
}