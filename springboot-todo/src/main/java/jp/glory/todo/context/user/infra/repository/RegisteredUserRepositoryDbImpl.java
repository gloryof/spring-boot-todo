package jp.glory.todo.context.user.infra.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import jp.glory.todo.context.base.infra.repository.DbRepository;
import jp.glory.todo.context.user.domain.entity.RegisteredUser;
import jp.glory.todo.context.user.domain.repository.RegisteredUserRepository;
import jp.glory.todo.context.user.domain.value.LoginId;
import jp.glory.todo.context.user.domain.value.Password;
import jp.glory.todo.context.user.domain.value.UserId;
import jp.glory.todo.context.user.domain.value.UserName;
import jp.glory.todo.external.db.user.dao.UsersDao;
import jp.glory.todo.external.db.user.entity.UsersTable;

/**
 * 登録済みユーザリポジトリ.
 * 
 * @author Junki Yamada
 *
 */
@DbRepository
public class RegisteredUserRepositoryDbImpl implements RegisteredUserRepository {

    /**
     * usersテーブルDAO.
     */
    private final UsersDao dao;

    /**
     * コンストラクタ.
     * @param dao usersテーブルDAO.
     */
    @Autowired
    public RegisteredUserRepositoryDbImpl(final UsersDao dao) {

        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RegisteredUser> findAll() {

        return dao.selectAll().stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(final RegisteredUser user) {

        dao.update(convertToRecord(user));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<RegisteredUser> findBy(UserId userId) {

        return dao.selectById(userId.getValue())
                .map(this::convertToEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<RegisteredUser> findBy(LoginId loginId) {

        return dao.selectByLoginId(loginId.getValue())
            .map(this::convertToEntity);
    }

    /**
     * usersテーブルからエンティティに変換する.
     * @param record usersテーブルレコード
     * @return エンティティ
     */
    private RegisteredUser convertToEntity(final UsersTable record) {

        return new RegisteredUser(new UserId(record.getUserId()), new LoginId(record.getLoginId()),
                new UserName(record.getUserName()), new Password(record.getPassword()));
    }

    /**
     * エンティティからusersテーブルのレコードに変換する.
     * @param user ユーザ
     * @return usersテーブルレコード
     */
    private UsersTable convertToRecord(final RegisteredUser user) {

        final UsersTable record = new UsersTable();

        record.setUserId(user.getUserId().getValue());
        record.setLoginId(user.getLoginId().getValue());
        record.setUserName(user.getUserName().getValue());
        record.setPassword(user.getPassword().getValue());

        return record;
    }
}
