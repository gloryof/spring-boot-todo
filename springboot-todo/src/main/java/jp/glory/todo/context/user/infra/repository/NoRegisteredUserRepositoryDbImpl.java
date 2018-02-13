package jp.glory.todo.context.user.infra.repository;

import org.springframework.beans.factory.annotation.Autowired;

import jp.glory.todo.context.base.infra.repository.DbRepository;
import jp.glory.todo.context.user.domain.entity.NoRegisteredUser;
import jp.glory.todo.context.user.domain.repository.NoRegisteredUserRepository;
import jp.glory.todo.context.user.domain.value.UserId;
import jp.glory.todo.external.db.user.dao.UsersDao;
import jp.glory.todo.external.db.user.entity.UsersTable;

/**
 * {@link NoRegisteredUserRepository}のDB実装.
 * @author gloryof
 *
 */
@DbRepository
public class NoRegisteredUserRepositoryDbImpl implements NoRegisteredUserRepository {

    /**
     * ユーザDAO.
     */
    private final UsersDao dao;

    /**
     * コンストラクタ.
     * @param dao ユーザDAO
     */
    @Autowired
    public NoRegisteredUserRepositoryDbImpl(final UsersDao dao) {

        this.dao = dao;
    }

    @Override
    public UserId save(final NoRegisteredUser user) {

        final UserId newUserId = new UserId(dao.selectUserId());

        dao.insert(convertToRecord(user, newUserId));

        return newUserId;
    }

    /**
     * エンティティからusersテーブルのレコードに変換する.
     * @param newUserId 新しいユーザID
     * @return usersテーブルレコード
     */
    private UsersTable convertToRecord(final NoRegisteredUser user, final UserId userId) {

        final UsersTable record = new UsersTable();

        record.setUserId(userId.getValue());
        record.setLoginId(user.getLoginId().getValue());
        record.setUserName(user.getUserName().getValue());
        record.setPassword(user.getPassword().getValue());

        return record;
    }
}
