package jp.glory.todo.context.todo.domain.entity;

import java.util.Optional;

import jp.glory.todo.context.todo.domain.value.Memo;
import jp.glory.todo.context.todo.domain.value.Summary;
import jp.glory.todo.context.todo.domain.value.TodoId;
import jp.glory.todo.context.user.domain.value.UserId;
import lombok.Getter;

/**
 * TODOの内容.
 * 
 * @author Junki Yamada
 *
 */
public class Todo {

    /**
     * ラベル.
     */
    public static final String LABEL = "TODO";

    /**
     * ID.
     */
    @Getter
    private final TodoId id;

    /**
     * ユーザID.
     */
    @Getter
    private final UserId userId;

    /**
     * 概要.
     */
    @Getter
    private final Summary summary;

    /**
     * メモ.
     */
    @Getter
    private Optional<Memo> memo = Optional.empty();

    /**
     * バージョン.
     */
    @Getter
    private long entityVersion = 1l;

    /**
     * 完了フラグ.<br>
     * 完了している場合：true、完了していない場合：false
     */
    @Getter
    private boolean completed = false;

    /**
     * コンストラクタ.
     * 
     * @param id
     *            TODOのID
     * @param userId
     *            ユーザID
     * @param summary
     *            概要
     */
    public Todo(final TodoId id, final UserId userId, final Summary summary) {

        this.id = id;
        this.userId = userId;
        this.summary = summary;
    }

    /**
     * 登録済みのTODOかを判定する.
     * 
     * @return 登録済みの場合：true、未登録の場合：false
     */
    public boolean isRegistered() {

        return id.isSetValue();
    }

    /**
     * 完了済みとしてマークする.
     */
    public void markAsComplete() {

        this.completed = true;
    }

    /**
     * 完了済みのマークを解除する.
     */
    public void unmarkFromComplete() {

        this.completed = false;
    }

    /**
     * バージョンを設定したエンティティを作成する.
     * @param version バージョン
     */
    public void version(long version) {

        this.entityVersion = version;
    }

    /**
     * メモを設定する.
     * @param memo メモ
     */
    public void setMemo(final Memo memo) {

        this.memo = Optional.of(memo);
    }
}
