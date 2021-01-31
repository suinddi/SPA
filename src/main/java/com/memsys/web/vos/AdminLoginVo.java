package com.memsys.web.vos;

import com.memsys.utility.Sha512;

public class AdminLoginVo {
    private static final String EMAIL_REGEX = "^(?=.{4,100}$)(?!.*[\\-]{2,}$)(?!.*[_]{2,}$)(?!.*[.]{2,}$)([0-9a-zA-Z][0-9a-zA-Z\\-_.]*[0-9a-zA-Z])@([0-9a-z][0-9a-z\\-]*[0-9a-zA-Z])\\.([a-z]{2,15})(\\.[a-z]{2})?$";
    private static final String PASSWORD_REGEX = "^([0-9a-zA-Z~!@#$%^&*()\\-_=+\\[{\\]}\\\\|;:\",<.>/?]{4,100})$";

    private final String email;
    private final String password;
    private final String hashedPassword;
    private final boolean isNormalized;

    public AdminLoginVo(String email, String password) {
        if (email.matches(AdminLoginVo.EMAIL_REGEX) && password.matches(AdminLoginVo.PASSWORD_REGEX)) {
            this.email = email;
            this.password = password;
            this.hashedPassword = Sha512.hash(this.password, "");
            this.isNormalized = true;
        } else {
            this.email = null;
            this.password = null;
            this.hashedPassword = null;
            this.isNormalized = false;
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public boolean isNormalized() {
        return isNormalized;
    }
}