package jp.glory.domain.user.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import jp.glory.domain.user.entity.User;
import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.UserId;

public class UserRepositoryMock implements UserRepository {

    private final Map<Long, User> userMap = new HashMap<>();

    private long sequence = 1;

    @Override
    public List<User> findAll() {
        return userMap.entrySet().stream().sorted((v1, v2) -> v1.getKey() < v2.getKey() ? -1 : 1).map(v -> v.getValue())
                .collect(Collectors.toList());
    }

    @Override
    public UserId save(final User user) {

        final User saveUser;
        if (!user.isRegistered()) {

            saveUser = new User(new UserId(sequence), user.getLoginId(), user.getUserName(), user.getPassword());
            sequence++;

        } else {

            saveUser = user;
        }
        userMap.put(saveUser.getUserId().getValue(), saveUser);

        return saveUser.getUserId();
    }

    @Override
    public Optional<User> findBy(final UserId userId) {

        return Optional.ofNullable(userMap.get(userId.getValue()));
    }

    @Override
    public Optional<User> findBy(final LoginId loginId) {
        return userMap.entrySet().stream().map(e -> e.getValue())
                .filter(v -> v.getLoginId().getValue().equals(loginId.getValue())).findAny();
    }

    public long getCurrentSequence() {

        return sequence;
    }
}