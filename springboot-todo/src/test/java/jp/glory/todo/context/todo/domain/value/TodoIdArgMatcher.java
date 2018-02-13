package jp.glory.todo.context.todo.domain.value;

import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

public class TodoIdArgMatcher implements ArgumentMatcher<TodoId> {

    private final TodoId value;

    private TodoIdArgMatcher(final TodoId value) {

        this.value = value;
    }

    @Override
    public boolean matches(final TodoId argument) {

        return value.isSame(argument);
    }

    public static TodoId arg(final long todoId) {

        return Mockito.argThat(new TodoIdArgMatcher(new TodoId(todoId)));
    }

}
