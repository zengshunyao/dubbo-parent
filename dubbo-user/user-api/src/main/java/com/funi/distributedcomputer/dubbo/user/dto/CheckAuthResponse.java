package com.funi.distributedcomputer.dubbo.user.dto;

import com.funi.distributedcomputer.dubbo.user.abs.AbstractResponse;

public class CheckAuthResponse extends AbstractResponse {


    private static final long serialVersionUID = 1460305820845509173L;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "CheckAuthResponse{" +
                "uid='" + uid + '\'' +
                '}';
    }
}
