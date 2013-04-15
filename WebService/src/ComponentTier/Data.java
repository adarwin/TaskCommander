package com.adarwin.csc435.ejb;

import com.adarwin.csc435.CustomizedLogger;
import com.adarwin.csc435.User;
import javax.ejb.Remote;

@Remote
public interface Data {
    public User getUser(String username, String password);
    public boolean isRegisteredUser(User user);
    public boolean isRegisteredUser(String username, String password);
    public boolean registerUser(String username, String password);
}
