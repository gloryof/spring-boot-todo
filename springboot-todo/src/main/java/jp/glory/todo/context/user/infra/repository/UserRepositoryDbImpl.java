package jp.glory.todo.context.user.infra.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import jp.glory.todo.context.base.infra.repository.DbRepository;
import jp.glory.todo.context.user.domain.entity.User;
import jp.glory.todo.context.user.domain.repository.UserRepository;
import jp.glory.todo.context.user.domain.value.LoginId;
import jp.glory.todo.context.user.domain.value.Password;
import jp.glory.todo.context.user.domain.value.UserId;
import jp.glory.todo.context.user.domain.value.UserName;
import jp.glory.todo.external.db.user.dao.UsersDao;
import jp.glory.todo.external.db.user.entity.UsersTable;

/**
 * ユーザリポジトリ.
 * 
 * @author Junki Yamada
 *
 */
@DbRepository
public class UserRepositoryDbImpl implements UserRepository {

    /**
     * usersテーブルDAO.
     */
    private final UsersDao dao;

    /**
     * コンストラクタ.
     * @param dao usersテーブルDAO.
     */
    @Autowired
    public UserRepositoryDbImpl(final UsersDao dao) {

        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAll() {

        return dao.selectAll().stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserId save(final User user) {

        final UserId savedUserId;
        if (!user.isRegistered()) {

            savedUserId = new UserId(dao.selectUserId());

            dao.insert(convertToRecord(savedUserId, user));
        } else {

            savedUserId = user.getUserId();

            dao.update(convertToRecord(savedUserId, user));
        }

        return savedUserId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> findBy(UserId userId) {

        return dao.selectById(userId.getValue())
            .map(v -> Optional.of(convertToEntity(v)))
            .orElse(Optional.empty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> findBy(LoginId loginId) {

        return dao.selectByLoginId(loginId.getValue())
            .map(v -> Optional.of(convertToEntity(v)))
            .orElse(Optional.empty());
    }

    /**
     * usersテーブルからエンティティに変換する.
     * @param record usersテーブルレコード
     * @return エンティティ
     */
    private User convertToEntity(final UsersTable record) {

        return new User(new UserId(record.getUserId()), new LoginId(record.getLoginId()),
                new UserName(record.getUserName()), new Password(record.getPassword()));
    }

    /**
     * エンティティからusersテーブルのレコードに変換する.
     * @param newUserId 新しいユーザID
     * @param user ユーザ
     * @return usersテーブルレコード
     */
    private UsersTable convertToRecord(final UserId newUserId, final User user) {

        final UsersTable record = new UsersTable();

        record.setUserId(newUserId.getValue());
        record.setLoginId(user.getLoginId().getValue());
        record.setUserName(user.getUserName().getValue());
        record.setPassword(user.getPassword().getValue());

        return record;
    }
}
