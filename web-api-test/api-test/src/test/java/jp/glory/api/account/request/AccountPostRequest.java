package jp.glory.api.account.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AccountPostRequest {

    private static final String LOGIN_ID = "loginId";
    private static final String USER_NAME = "userName";
    private static final String PASSWORD = "password";

    private String loginId = null;
    private String userName = null;
    private String password = null;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, String> toMap() {

        final Map<String, String> returnMap = new HashMap<>();

        returnMap.put(LOGIN_ID, loginId);
        returnMap.put(USER_NAME, userName);
        returnMap.put(PASSWORD, password);

        return Collections.unmodifiableMap(returnMap);
    }
}
