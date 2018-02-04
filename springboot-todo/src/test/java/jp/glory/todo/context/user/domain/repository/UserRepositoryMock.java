package jp.glory.todo.context.user.domain.repository;

import jp.glory.todo.context.user.infra.repository.UserRepositoryInMemoryImpl;

public class UserRepositoryMock extends UserRepositoryInMemoryImpl {

    private long mockSequence = 1;

    public void setSequence(final long value) {

        mockSequence = value;
    }

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
}