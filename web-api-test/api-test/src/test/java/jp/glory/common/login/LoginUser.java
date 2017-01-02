package jp.glory.common.login;

public class LoginUser {

    private final String loginId;
    private final String userName;
    private final String password;

    public LoginUser(final String loginId, final String userName, final String password) {

        this.loginId = loginId;
        this.userName = userName;
        this.password = password;
    }

    
    public static LoginUser as(final String loginId, final String password) {

        return new LoginUser(loginId, "", password);
    }

    public String getLoginId() {
        return loginId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
