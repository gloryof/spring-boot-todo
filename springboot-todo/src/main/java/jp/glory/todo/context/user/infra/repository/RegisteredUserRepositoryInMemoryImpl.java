package jp.glory.todo.context.user.infra.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import jp.glory.todo.context.base.infra.repository.InMemoryRepository;
import jp.glory.todo.context.user.domain.entity.NoRegisteredUser;
import jp.glory.todo.context.user.domain.entity.RegisteredUser;
import jp.glory.todo.context.user.domain.repository.RegisteredUserRepository;
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
public class RegisteredUserRepositoryInMemoryImpl implements RegisteredUserRepository {

    /**
     * シーケンス.
     */
    private long sequence = 1;

    /**
     * ユーザのマップ
     */
    private final Map<Long, RegisteredUser> userMap = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RegisteredUser> findAll() {

        return userMap.entrySet().stream()
                .sorted((v1, v2) -> v1.getKey() < v2.getKey() ? -1 : 1)
                .map(v -> copy(v.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public void save(RegisteredUser user) {

        final RegisteredUser saveUser;
        saveUser = copy(user);

        userMap.put(saveUser.getUserId().getValue(), saveUser);
    }

    @Override
    public Optional<RegisteredUser> findBy(UserId userId) {

        return Optional.ofNullable(userMap.get(userId.getValue()));
    }

    @Override
    public Optional<RegisteredUser> findBy(LoginId loginId) {

        return userMap.entrySet().stream()
                .map(e -> e.getValue())
                .filter(v -> v.getLoginId().getValue().equals(loginId.getValue()))
                .findAny();
    }

    /**
     * 未登録のユーザを登録する.
     * @param user 未登録のユーザ
     * @return ユーザID
     */
    public UserId register(final NoRegisteredUser user) {

        final UserId userId = new UserId(getSequence());

        incrementSequence();

        save(createUser(user, userId));

        return userId;
    }

    /**
     * 内容をコピーする.
     * @param src 元オブジェクト
     * @return コピーオブジェクト
     */
    private RegisteredUser copy(final RegisteredUser src) {

        return new RegisteredUser(
                new UserId(src.getUserId().getValue()),
                new LoginId(src.getLoginId().getValue()),
                new UserName(src.getUserName().getValue()),
                new Password(src.getPassword().getValue()));
    }


    /**
     * 登録済みユーザを作成する.
     * @param user ユーザ
     * @param newUserId 新しいユーザID
     * @return
     */
    private RegisteredUser createUser(final NoRegisteredUser user, final UserId newUserId) {

        return new RegisteredUser(newUserId, user.getLoginId(), user.getUserName(), user.getPassword());
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
}
