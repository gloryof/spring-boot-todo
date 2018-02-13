package jp.glory.todo.context.user.infra.repository;

import jp.glory.todo.context.base.infra.repository.InMemoryRepository;
import jp.glory.todo.context.user.domain.entity.NoRegisteredUser;
import jp.glory.todo.context.user.domain.repository.NoRegisteredUserRepository;
import jp.glory.todo.context.user.domain.value.UserId;

/**
 * {@link NoRegisteredUserRepository}のインメモリ実装.
 * @author Junki Yamada
 *
 */
@InMemoryRepository
public class NoRegisteredUserRepositoryInMemoryImpl implements NoRegisteredUserRepository {


    private final RegisteredUserRepositoryInMemoryImpl registeredRepository;

    public NoRegisteredUserRepositoryInMemoryImpl(final RegisteredUserRepositoryInMemoryImpl registeredRepository) {

        this.registeredRepository = registeredRepository;
    }

    @Override
    public UserId save(final NoRegisteredUser user) {

        return registeredRepository.register(user);
    }
}
