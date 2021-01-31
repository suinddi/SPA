package com.memsys.web.vos;

public class LoginAttemptVo {
    private final String ip;
    private final String email;
    private final String password;
    private final String loginResult;

    public LoginAttemptVo(String ip, String email, String password, String loginResult) {
        this.ip = ip;
        this.email = email;
        this.password = password;
        this.loginResult = loginResult;
    }

    public String getIp() {
        return ip;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLoginResult() {
        return loginResult;
    }
}
