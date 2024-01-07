package com.lucendar.gnss.sdk.config;

import info.gratour.common.error.ErrorWithCode;
import info.gratour.common.error.Errors;

import java.util.StringJoiner;

public class StrmMediaConfig implements Cloneable {

    private String instanceId;
    private boolean disabled;
    private String apiUrl;
    private String username;
    private String password;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void validateAndSettle() {
        if (instanceId == null || instanceId.isEmpty())
            throw new ErrorWithCode(Errors.INVALID_CONFIG, "strm.instanceId");

        if (!disabled) {
            if (apiUrl == null || apiUrl.isEmpty())
                throw new ErrorWithCode(Errors.INVALID_CONFIG, "strm.apiUrl");

            if (username == null || username.isEmpty())
                throw new ErrorWithCode(Errors.INVALID_CONFIG, "strm.username");

            if (password == null || password.isEmpty())
                throw new ErrorWithCode(Errors.INVALID_CONFIG, "strm.password");
        }
    }


    @Override
    public StrmMediaConfig clone() {
        try {
            return (StrmMediaConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", StrmMediaConfig.class.getSimpleName() + "[", "]")
                .add("instanceId='" + instanceId + "'")
                .add("disabled=" + disabled)
                .add("apiUrl='" + apiUrl + "'")
                .add("username='" + username + "'")
                .add("password='" + password + "'")
                .toString();
    }
}
