package jp.glory.test.tool.script.todo;

import java.time.LocalDateTime;
import java.util.Optional;

public class TodoData {

    private final Optional<Integer> id;
    private final Summary summary;
    private Memo memo = new Memo("テストメモ-" + LocalDateTime.now().toString());;
    private boolean completed = false;

    public TodoData(final Summary summary) {

        this.id = Optional.empty();
        this.summary = summary;
    }

    TodoData(final Integer id, final TodoData base) {

        this.id = Optional.of(id);
        this.summary = base.summary;
        this.memo = base.memo;
        this.completed = base.completed;
    }

    public TodoData memo(final Memo memo) {

        this.memo = memo;

        return this;
    }

    public TodoData complete() {

        this.completed = true;

        return this;
    }

    public Memo getMemo() {
        return memo;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Optional<Integer> getId() {
        return id;
    }

    public Summary getSummary() {
        return summary;
    }
}
