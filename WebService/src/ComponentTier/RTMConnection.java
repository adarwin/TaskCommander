package com.adarwin.csc435.ejb;

import javax.ejb.Local;
import javax.jws.WebMethod;
import javax.jws.WebService;

@Local
//@WebService
public interface RTMConnection {
    public void login();
    public String getFrob();
    public String getAuthenticationURL();
    public String getToken();
    public void addTask(String taskName);
    public String md5(String message);
    //public String getToken();
}
