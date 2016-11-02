package jp.glory.domain.user.repository;

import jp.glory.infra.inmemory.repository.user.UserRepositoryInMemoryImpl;

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