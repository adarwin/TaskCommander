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
    @EJB
    private RTMConnection rtmConnectionBean;


    public void log(Exception ex) {
        logbook.log(logHeader, ex);
    }
    public void log(String level, String message) {
        logbook.log(level, logHeader, message);
    }



    @Override
    public boolean isLoggedIn(User user) {
        //log(Logbook.INFO, "Checking to see if " + user + " is logged in");
        boolean loggedIn = false;
        if (user != null) {
            loggedIn = user.getLoggedIn();
            //log(Logbook.INFO, user + " is logged in");
        } else {
            //log(Logbook.INFO, user + " is not logged in");
        }
        return loggedIn;
    }



    @Override
    public boolean isRegisteredUser(String username, String password) {
        log(Logbook.INFO, "Checking to see if " + username + " is a " +
                          "registered user");
        boolean registered = false;
        registered = dataBean.isRegisteredUser(username, password);
        if (registered) {
            log(Logbook.INFO, username + " is a registered user");
        } else {
            log(Logbook.INFO, username + " is not a registered user");
        }
        return registered;
    }


    @Override
    public User logUserIn(String username, String password) {
        log(Logbook.INFO, "Attempting to log " + username + " in");
        if (rtmConnectionBean != null) {
            rtmConnectionBean.login();
        } else {
            log(Logbook.INFO, "rtmConnectionBean == null");
        }
        User user = dataBean.getUser(username, password);
        if (user == null) {
            log(Logbook.WARNING, "Failed to log " + username + " in");
        } else {
            user.setLoggedIn(true);
        }
        return user;
    }


    @Override
    public User logUserOut(User user) {
        // Assume the input user object is a copy and we should update
        // the official stored user
        if (user == null) {
            log(Logbook.WARNING, "Can't log out a null user");
        } else {
            user = dataBean.logUserOut(user);
            /*
            user.setLoggedIn(false);
            User realUser = dataBean.getUser(user.getUsername(), user.getPassword());
            realUser.updateFrom(user);
            */
            log(Logbook.INFO, "Logged " + user + " out");
        }
        return user;
    }



    @Override
    public boolean registerUser(String username, String password) {
        return dataBean.registerUser(username, password);
    }


}
