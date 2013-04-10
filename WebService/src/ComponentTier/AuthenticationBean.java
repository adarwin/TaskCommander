package com.adarwin.csc435.ejb;

import com.adarwin.logging.Logbook;
import com.adarwin.csc435.User;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class AuthenticationBean
{
    private static Logbook logbook = new Logbook("../logs/TaskCommander.log");
    private final String logHeader = "AuthenticationBean";
    @EJB
    private DataBean dataBean;


    private void log(Exception ex) {
        logbook.log(logHeader, ex);
    }
    private void log(String level, String message) {
        logbook.log(level, logHeader, message);
    }


    public boolean test() {
        return false;
    }


    //@Override
    public boolean isLoggedIn(User user) {
        boolean loggedIn = false;
        if (user != null) {
            loggedIn = user.getLoggedIn();
        }
        return loggedIn;
    }



    public boolean isRegisteredUser(String username, String password) {
        return dataBean.isRegisteredUser(username, password);
    }


    public User logUserIn(String username, String password) {
        User user = dataBean.getUser(username, password);
        return user;
    }


    public User logUserOut(User user) {
        return user;
    }


}
