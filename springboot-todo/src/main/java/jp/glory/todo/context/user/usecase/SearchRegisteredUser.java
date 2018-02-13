package jp.glory.todo.context.user.usecase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import jp.glory.todo.context.base.usecase.Usecase;
import jp.glory.todo.context.user.domain.entity.RegisteredUser;
import jp.glory.todo.context.user.domain.repository.RegisteredUserRepository;
import jp.glory.todo.context.user.domain.value.LoginId;

/**
 * 登録済みユーザの検索を行う.
 * @author Junki Yamada
 *
 */
@Usecase
public class SearchRegisteredUser {

    /**
     * リポジトリ.
     */
    private RegisteredUserRepository repository;

    /**
     * コンストラクタ.
     * @param repository リポジトリ
     */
    @Autowired
    public SearchRegisteredUser(final RegisteredUserRepository repository) {

        this.repository = repository;
    }

    /**
     * ログインIDで検索を行う.
     * @param loginId ログインID
     * @return
     */
    public Optional<RegisteredUser> findBy(final LoginId loginId) {

        return repository.findBy(loginId);
    }
}
