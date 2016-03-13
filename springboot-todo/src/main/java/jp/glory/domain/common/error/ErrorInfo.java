package jp.glory.domain.common.error;

/**
 * エラー情報.
 * 
 * @author Junki Yamada
 */
public enum ErrorInfo {

    /** 必須入力チェックエラー. */
    Required("{0}は必須です。"),

    /** 文字数オーバーエラー. */
    MaxLengthOver("{0}は{1}文字以内で入力してください。"),

    /** 使用不可能文字列エラー. */
    InvalidCharacters("{0}に使用できない文字列が含まれています。"),

    /** 未登録エラー. */
    NotRegister("登録されていない{0}です。"),

    /**
     * ログインエラー.
     */
    LoginFailed("ログインIDとパスワードの組み合わせが間違っています。"),

    /**
     * ログインID重複エラー.
     */
    LoginIdDuplicated("既に使用されているログインIDです。"),

    /**
     * 他ユーザへのTODO登録エラー.
     */
    SavedToOtherUser("他ユーザのTODOには保存はできません。");

    /** メッセージ. */
    public final String message;

    /**
     * コンストラクタ.
     * 
     * @param paramMessage
     *            メッセージ
     */
    private ErrorInfo(final String paramMessage) {

        message = paramMessage;
    }
}