package com.adarwin.csc435.ejb;

import com.adarwin.logging.Logbook;
import com.adarwin.csc435.Task;
import com.adarwin.csc435.User;
import com.adarwin.csc435.UserAlreadyExistsException;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;

@Singleton
public class DataBean {

    private Logbook logbook = new Logbook("../logs/TaskCommander.log");
    private final String logHeader = "DataBean";
    private List<User> usersList;

    private void log(Exception ex) {
        logbook.log(logHeader, ex);
    }
    private void log(String level, String message) {
        logbook.log(level, logHeader, message);
    }



    @PostConstruct
    public void init() {
        usersList = new ArrayList<User>();
    }


    public User getUser(String username, String password) {
        User outputUser = null;
        List<User> users = getUsersList();
        for (User user : users) {
            if (user.getUsername().equals(username) &&
                    user.getPassword().equals(password)) {
                outputUser = user;
            }
        }
        return outputUser;
    }


    public boolean isRegisteredUser(User user) {
        boolean registered = false;
        List<User> users = getUsersList();
        if (users.contains(user)) {
            registered = true;
        }
        return registered;
    }


    public boolean isRegisteredUser(String username, String password) {
        User user = getUser(username, password);
        return user != null;
    }


    public boolean registerUser(String username, String password) {
        boolean successful = true;
        if (userExists(username)) {
            successful = false;
            //throw new UserAlreadyExistsException("The username '" + username + "' is already in use.");
        }
        List<User> users = getUsersList();
        User user = new User(username);
        user.setPassword(password);
        Task newTask = new Task("New User Orientation");
        newTask.setDueDate("Today");
        user.addTask(newTask);
        users.add(user);
        return successful;
    }


    private boolean userExists(String username) {
        boolean userExists = false;
        List<User> users = getUsersList();
        if (users != null) {
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    userExists = true;
                }
            }
        }
        return userExists;
    }



    private List<User> getUsersList() {
        return usersList;
    }
}
