package com.adarwin.csc435.ejb;

import javax.ejb.Local;

@Local
public interface RTMConnection {
    public void login();
    public String getFrob();
    public String getAuthenticationURL();
    public String getToken();
    public void addTask(String taskName);
    //public String getToken();
}
