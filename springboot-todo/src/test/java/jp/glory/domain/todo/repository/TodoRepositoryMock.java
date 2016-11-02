package jp.glory.domain.todo.repository;

import java.util.Optional;

import jp.glory.domain.todo.entity.Todo;
import jp.glory.domain.todo.value.TodoId;
import jp.glory.infra.inmemory.repository.todo.TodoRepositoryInMemoryImpl;

public class TodoRepositoryMock extends TodoRepositoryInMemoryImpl {

    private long mockSequence = 1;

    public long getCurrentSequence() {

        return mockSequence;
    }

    @Override
    protected long getSequence() {
        return mockSequence;
    }

    @Override
    protected void incrementSequence() {

        mockSequence++;
    }

    public void setSequence(final long value) {

        mockSequence = value;
    }

    public Optional<Todo> getResult(final long id) {

        return findBy(new TodoId(id));
    }

    public TodoId addTestData(final Todo todo) {

        setSequence(todo.getId().getValue());

        final Todo newTodo = new Todo(TodoId.notNumberingValue(),
                                todo.getUserId(),
                                todo.getSummary(),
                                todo.getMemo(),
                                todo.isCompleted());

        return super.save(newTodo);
    }
}
