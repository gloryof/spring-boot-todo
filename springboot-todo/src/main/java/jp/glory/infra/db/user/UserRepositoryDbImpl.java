package jp.glory.infra.db.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import jp.glory.domain.user.entity.User;
import jp.glory.domain.user.repository.UserRepository;
import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.UserId;

/**
 * ユーザリポジトリ.
 * 
 * @author Junki Yamada
 *
 */
@Repository
public class UserRepositoryDbImpl implements UserRepository {

    private static final Map<Long, User> users = new HashMap<>();

    private static long sequence = 1;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAll() {

        return users.entrySet().stream()
            .map(e -> e.getValue())
            .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserId save(final User user) {

        final long userIdValue;
        if (!user.getUserId().isSetValue()) {

            userIdValue = sequence;
            sequence++;
        } else {

            userIdValue = user.getUserId().getValue();
        }

        users.put(userIdValue, user);

        return new UserId(sequence);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> findBy(UserId userId) {

        return users.entrySet().stream()
            .filter(e -> e.getKey().equals(userId.getValue()))
            .map(e -> e.getValue())
            .findAny();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> findBy(LoginId loginId) {

        return users.entrySet().stream()
                .filter(e -> e.getValue().getLoginId().getValue().equals(loginId.getValue()))
                .map(e -> e.getValue())
                .findAny();
    }

}
