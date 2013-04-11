package com.adarwin.csc435.ejb;

import com.adarwin.logging.Logbook;
import com.adarwin.csc435.User;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class AuthenticationBean implements Authentication {
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



    @Override
    public boolean isLoggedIn(User user) {
        log(Logbook.INFO, "Checking to see if user is logged in");
        boolean loggedIn = false;
        if (user != null) {
            loggedIn = user.getLoggedIn();
        }
        if (loggedIn) {
            log(Logbook.INFO, "Determined user was logged in");
        } else {
            log(Logbook.INFO, "Determined user was not logged in");
        }
        return loggedIn;
    }



    @Override
    public boolean isRegisteredUser(String username, String password) {
        return dataBean.isRegisteredUser(username, password);
    }


    @Override
    public User logUserIn(String username, String password) {
        User user = dataBean.getUser(username, password);
        return user;
    }


    @Override
    public User logUserOut(User user) {
        return user;
    }


}
