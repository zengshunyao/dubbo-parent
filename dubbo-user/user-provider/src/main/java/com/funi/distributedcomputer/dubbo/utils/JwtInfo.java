package com.funi.distributedcomputer.dubbo.utils;

public class JwtInfo {
    private String uid;

    public JwtInfo() {
    }

    public JwtInfo(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
