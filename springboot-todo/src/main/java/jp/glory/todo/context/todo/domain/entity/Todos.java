package jp.glory.todo.context.todo.domain.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * TODOリスト.
 * 
 * @author Junki Yamada
 *
 */
public class Todos {

    /**
     * リスト.
     */
    private final List<Todo> todos;

    /**
     * サマリ.
     */
    @Getter
    private final Statistics statistics;

    /**
     * コンストラクタ.
     * 
     * @param todos
     *            TODOリスト
     */
    public Todos(final List<Todo> todos) {

        if (todos == null) {

            this.todos = new ArrayList<>();
        } else {

            this.todos = new ArrayList<>(todos);
        }

        this.statistics = new Statistics(this.todos);
    }

    /**
     * 未実行TODOがあるかを判定する.
     * 
     * @return 存在する場合：true、存在しない場合：false
     */
    public boolean hasUnexecuted() {

        return (0 < statistics.unexecuted);
    }

    /**
     * リストとして変換する.
     * 
     * @return リスト
     */
    public List<Todo> asList() {

        return todos;
    }

    /**
     * 統計.
     * 
     * @author Junki Yamada
     *
     */
    public class Statistics {

        /**
         * 合計件数.
         */
        @Getter
        private final int total;

        /**
         * 実行済件数.
         */
        @Getter
        private final int executed;

        /**
         * 未実行件数.
         */
        @Getter
        private final int unexecuted;

        /**
         * コンストラクタ.
         * 
         * @param todos
         *            TODOリスト.
         */
        private Statistics(final List<Todo> todos) {

            total = todos.size();
            executed = (int) todos.stream().filter(v -> v.isCompleted()).count();
            unexecuted = (int) todos.stream().filter(v -> !v.isCompleted()).count();
        }
    }
}
