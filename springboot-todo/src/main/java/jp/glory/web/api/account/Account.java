package jp.glory.web.api.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import jp.glory.domain.common.error.ValidateErrors;
import jp.glory.domain.user.value.LoginId;
import jp.glory.domain.user.value.Password;
import jp.glory.domain.user.value.UserName;
import jp.glory.framework.web.exception.InvalidRequestException;
import jp.glory.infra.encryption.Encryption;
import jp.glory.usecase.user.CreateNewAccount;
import jp.glory.usecase.user.CreateNewAccount.Result;
import jp.glory.web.api.ApiPaths;
import jp.glory.web.api.account.request.NewAccountRequest;

/**
 * アカウントAPI.
 * 
 * @author Junki Yamada
 *
 */
@RestController
@RequestMapping(value = ApiPaths.Account.PATH)
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class Account {

    /**
     * 新アカウント作成ユースケース.
     */
    private final CreateNewAccount createNewAccount;

    /**
     * 暗号化クラス.
     */
    private final Encryption encryption;

    /**
     * コンストラクタ.
     * 
     * @param createNewAccount
     *            新アカウント作成ユースケース
     */
    @Autowired
    public Account(final CreateNewAccount createNewAccount, final Encryption encryption) {

        this.createNewAccount = createNewAccount;
        this.encryption = encryption;
    }

    /**
     * アカウント作成.
     * 
     * @param requst
     *            アカウント作成リクエスト
     * @return 正常に登録できた場合:Created、入力不備がある場合：Bad Reauest
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> create(final NewAccountRequest requst) {

        final ValidateErrors errors = executeCreating(requst);

        if (errors.hasError()) {

            throw new InvalidRequestException(errors);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 作成処理を実行する.
     * 
     * @param requst
     *            アカウント作成リクエスト
     * @return 入力チェック結果
     */
    private ValidateErrors executeCreating(final NewAccountRequest requst) {

        final LoginId loginId = new LoginId(requst.getLoginId());
        final UserName userName = new UserName(requst.getUserName());
        final Password password = encryption.encrypt(requst.getPassword());

        final Result result = createNewAccount.create(loginId, userName, password);

        return result.getErrors();
    }
}
