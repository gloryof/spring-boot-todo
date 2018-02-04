package jp.glory.todo.context.todo.domain.value;

import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import jp.glory.todo.context.todo.domain.value.TodoId;

public class TodoIdArgMatcher extends ArgumentMatcher<TodoId> {

    private final TodoId value;

    private TodoIdArgMatcher(final TodoId value) {

        this.value = value;
    }

    @Override
    public boolean matches(final Object argument) {

        if (argument instanceof TodoId) {

            final TodoId compare = (TodoId) argument;

            return value.isSame(compare);
        }

        return false;
    }

    public static TodoId arg(final long todoId) {

        return Mockito.argThat(new TodoIdArgMatcher(new TodoId(todoId)));
    }
}
