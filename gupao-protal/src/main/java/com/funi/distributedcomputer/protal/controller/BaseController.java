package com.funi.distributedcomputer.protal.controller;

public abstract class BaseController {

    //    不能存在这儿，因为分布式部署有多台，最好存redis
    private ThreadLocal<String> uidLocal = new ThreadLocal<String>();

    /**
     * @return
     */
    public String getUid() {
        return uidLocal.get();
    }

    /**
     * @param uid
     */
    public void setUid(String uid) {
        this.uidLocal.set(uid);
    }

    /**
     * 清除uid
     */
    public void clearUid() {
        uidLocal.remove();
    }
}
