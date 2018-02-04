package jp.glory.todo.context.user.infra.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import jp.glory.todo.context.base.infra.repository.InMemoryRepository;
import jp.glory.todo.context.user.domain.entity.User;
import jp.glory.todo.context.user.domain.repository.UserRepository;
import jp.glory.todo.context.user.domain.value.LoginId;
import jp.glory.todo.context.user.domain.value.Password;
import jp.glory.todo.context.user.domain.value.UserId;
import jp.glory.todo.context.user.domain.value.UserName;

/**
 * インメモリで保持するユーザリポジトリ.
 * @author Junki Yamada
 *
 */
@InMemoryRepository
public class UserRepositoryInMemoryImpl implements UserRepository {

    /**
     * ユーザのマップ
     */
    private final Map<Long, User> userMap = new HashMap<>();

    /**
     * シーケンス.
     */
    private long sequence = 1;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAll() {

        return userMap.entrySet().stream()
                .sorted((v1, v2) -> v1.getKey() < v2.getKey() ? -1 : 1)
                .map(v -> copy(v.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public UserId save(User user) {

        final User saveUser;
        if (!user.isRegistered()) {

            saveUser = new User(new UserId(getSequence()), user.getLoginId(), user.getUserName(), user.getPassword());
            incrementSequence();

        } else {

            saveUser = copy(user);
        }
        userMap.put(saveUser.getUserId().getValue(), saveUser);

        return saveUser.getUserId();
    }

    @Override
    public Optional<User> findBy(UserId userId) {

        return Optional.ofNullable(userMap.get(userId.getValue()));
    }

    @Override
    public Optional<User> findBy(LoginId loginId) {

        return userMap.entrySet().stream()
                .map(e -> e.getValue())
                .filter(v -> v.getLoginId().getValue().equals(loginId.getValue()))
                .findAny();
    }

    /**
     * シーケンス取得.
     * @return シーケンス
     */
    protected long getSequence() {
        return sequence;
    }

    /**
     * シーケンスの増加.<br>
     * 継承して拡張するための拡張ポイント。
     */
    protected void incrementSequence() {

        sequence++;
    }

    /**
     * 内容をコピーする.
     * @param src 元オブジェクト
     * @return コピーオブジェクト
     */
    private User copy(final User src) {

        return new User(
                new UserId(src.getUserId().getValue()),
                new LoginId(src.getLoginId().getValue()),
                new UserName(src.getUserName().getValue()),
                new Password(src.getPassword().getValue()));
    }
}
