package com.memsys.web.vos;

import com.memsys.utility.Sha512;

public class RegisterVo {

    private static final String EMAIL_REGEX = "^(?=.{8,100}$)(?!.*[\\-]{2,}.*$)(?!.*[_]{2,}.*$)(?!.*[.]{2,}.*$)([0-9a-zA-Z][0-9a-zA-Z\\-_.]*[0-9a-zA-Z])@([a-z][a-z\\-]*[a-z])\\.([a-z]{2,15})(\\.[a-z]{2})?$";
    private static final String PASSWORD_REGEX = "^([0-9a-zA-Z~!@#$%^&*()\\-_=+\\[{\\]}\\\\|;:'\",<.>/?]{4,100})$";
    private static final String NAME_REGEX = "^([가-힣]{2,10})$";
    private static final String NICKNAME_REGEX = "^([가-힣]{2,10})$";
    private static final String CONTACT_REGEX = "^([0-9]{11})$";
    private static final String ADDRESS_REGEX = "^([0-9a-zA-Z가-힣 \\-]{4,250})$";
    private static final String BIRTH_REGEX = "^([0-9]{6})$";

    private final String email;
    private final String password;
    private final String hashedPassword;
    private final String name;
    private final String nickname;
    private final String contact;
    private final String address;
    private final String birth;
    private final boolean isAdmin;
    private final String status;


    private boolean isNormalized = false;


    public RegisterVo(String email, String password, String name, String nickname,
                      String contact, String address, String birth, String isAdmin, String status) {
        if(email.matches(RegisterVo.EMAIL_REGEX) && password.matches(RegisterVo.PASSWORD_REGEX) &&
                name.matches(RegisterVo.NAME_REGEX) && nickname.matches(RegisterVo.NICKNAME_REGEX) &&
                contact.matches(RegisterVo.CONTACT_REGEX) && address.matches(RegisterVo.ADDRESS_REGEX) && birth.matches(RegisterVo.BIRTH_REGEX)) {
            this.isNormalized = true;
            this.email = email;
            this.password = password;
            this.hashedPassword = Sha512.hash(this.password);
            this.name = name;
            this.nickname = nickname;
            this.contact = contact;
            this.address = address;
            this.birth = birth;
            this.isAdmin = isAdmin.equals("on");
            this.status = status;
        } else {
            this.email = null;
            this.password = null;
            this.hashedPassword = null;
            this.name = null;
            this.nickname = null;
            this.contact = null;
            this.address = null;
            this.birth = null;
            this.isAdmin = false;
            this.status = null;
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

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public String getBirth() {
        return birth;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public String getStatus() {
        return status;
    }

    public boolean isNormalized() {
        return isNormalized;
    }
}



