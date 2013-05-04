package com.adarwin.csc435.ejb;

import com.adarwin.csc435.User;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Remote
public interface Authentication {
    public boolean isLoggedIn(User user);
    public boolean isRegisteredUser(String username, String password);
    public User logUserIn(String username, String password);
    public User logUserOut(User user);
    public boolean registerUser(String username, String password);
}
