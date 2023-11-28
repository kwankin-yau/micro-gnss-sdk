/*******************************************************************************
 *  Copyright (c) 2019, 2021 lucendar.com.
 *  All rights reserved.
 *
 *  Contributors:
 *     KwanKin Yau (alphax@vip.163.com) - initial API and implementation
 *******************************************************************************/
package com.lucendar.gnss.sdk.session;

import com.lucendar.strm.common.StreamingApi;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Objects;
import java.util.StringJoiner;

public class User implements Cloneable {


    private String userName;
    private String password;
    private String pwdSeed;
    private String pwdMd5;
    private String appId;

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

    public String getPwdSeed() {
        return pwdSeed;
    }

    public void setPwdSeed(String pwdSeed) {
        this.pwdSeed = pwdSeed;
    }

    public String getPwdMd5() {
        return pwdMd5;
    }

    public void setPwdMd5(String pwdMd5) {
        this.pwdMd5 = pwdMd5;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public boolean validate(String password) {
        String s = pwdSeed + password;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5 = md.digest(s.getBytes(StandardCharsets.UTF_8));
            String hex = HexFormat.of().formatHex(md5);
            return Objects.equals(hex, pwdMd5);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String appIdDef() {
        if (appId != null)
            return StreamingApi.DEFAULT_APP_ID;
        else
            return null;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("userName='" + userName + "'")
                .add("password='" + password + "'")
                .add("pwdSeed='" + pwdSeed + "'")
                .add("pwdMd5='" + pwdMd5 + "'")
                .add("appId='" + appId + "'")
                .toString();
    }

    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
