package jp.glory.test.tool.script.todo;

import java.util.List;

public class TodoRegisterResult {

    private final List<TodoData> datas;

    TodoRegisterResult(final List<TodoData> datas) {

        this.datas = datas;
    }

    public List<TodoData> getDatas() {
        return datas;
    }

    public TodoData get(final int index) {

        return datas.get(index);
    }
}
