package com.memsys.web.vos;

import java.util.Date;

public class UserVo {
    private final int index;
    private final String email;
    private final String password;
    private final String name;
    private final String nickname;
    private final String contact;
    private final String address;
    private final String birth;
    private final boolean isAdmin;
    private final String status;
    private final Date createdAt;
    private final Date statusChangedAt;
    private final Date passwordModifiedAt;
    private final Date signedAt;

    public UserVo(int index, String email, String password, String name, String nickname, String contact, String address, String birth, boolean isAdmin, String status, Date createdAt, Date statusChangedAt, Date passwordModifiedAt, Date signedAt) {
        this.index = index;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.contact = contact;
        this.address = address;
        this.birth = birth;
        this.isAdmin = isAdmin;
        this.status = status;
        this.createdAt = createdAt;
        this.statusChangedAt = statusChangedAt;
        this.passwordModifiedAt = passwordModifiedAt;
        this.signedAt = signedAt;
    }

    public int getIndex() {
        return index;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getStatus() {
        return status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getStatusChangedAt() {
        return statusChangedAt;
    }

    public Date getPasswordModifiedAt() {
        return passwordModifiedAt;
    }

    public Date getSignedAt() {
        return signedAt;
    }
}