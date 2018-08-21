package com.funi.distributedcomputer.dubbo.user.dto;

import com.funi.distributedcomputer.dubbo.user.abs.AbstractRequest;

public class CheckAuthRequest extends AbstractRequest  {

    private static final long serialVersionUID = 2827571610680551275L;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "CheckAuthRequest{" +
                "token='" + token + '\'' +
                '}';
    }
}
