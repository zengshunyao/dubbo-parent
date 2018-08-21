package com.funi.distributedcomputer.protal.controller;

public abstract class BaseController {


    private ThreadLocal<String> uidLocal = new ThreadLocal<String>();

    public String getUid() {
        return uidLocal.get();
    }

    public void setUid(String uid) {
        this.uidLocal.set(uid);
    }
}
