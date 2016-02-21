package jp.glory.infra.db.user;

import java.util.List;
import java.util.Optional;

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
public class UserRepositoryImpl implements UserRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserId save(User user) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> findBy(UserId userId) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> findBy(LoginId loginId) {
        // TODO Auto-generated method stub
        return null;
    }

}
