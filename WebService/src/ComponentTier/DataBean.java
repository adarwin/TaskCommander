package com.adarwin.csc435.ejb;

import com.adarwin.logging.Logbook;
import com.adarwin.csc435.CustomizedLogger;
import com.adarwin.csc435.Task;
import com.adarwin.csc435.User;
import com.adarwin.csc435.UserAlreadyExistsException;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

//@Singleton
@Local
@Stateless
public class DataBean { //TODO: Figure out why we can't
                        //      implement CustomizedLogger here


    private Logbook logbook = new Logbook("../logs/TaskCommander.log");
    private final String logHeader = "DataBean";
    private List<User> usersList;

    //@PersistenceUnit
    //private EntityManagerFactory emFactory;
    @PersistenceContext(unitName = "usersPU")
    private EntityManager entityManager;

    public void log(Exception ex) {
        logbook.log(logHeader, ex);
    }
    public void log(String level, String message) {
        logbook.log(level, logHeader, message);
    }



    @PostConstruct
    public void init() {
        usersList = new ArrayList<User>();
        //entityManager = emFactory.createEntityManager();
    }



    public User getUser(String username, String password) {
        log(Logbook.INFO, "Attempting to get user: " + username);
        User outputUser = null;
        List<User> users = getUsersList();
        for (User user : users) {
            if (user.getUsername().equals(username) &&
                    user.getPassword().equals(password)) {
                outputUser = user;
                log(Logbook.INFO, "Found user: " + username);
            }
        }
        if (outputUser == null) {
            log(Logbook.WARNING, "Never found user: " + username);
        }
        return outputUser;
    }


    public boolean isRegisteredUser(User user) {
        log(Logbook.INFO, "Checking to see if " + user + " is a registered user");
        boolean registered = false;
        List<User> users = getUsersList();
        if (users.contains(user)) {
            registered = true;
            log(Logbook.INFO, user + " is a registered user");
        } else {
            log(Logbook.INFO, user + " is not a registered user");
        }
        return registered;
    }


    public boolean isRegisteredUser(String username, String password) {
        log(Logbook.INFO, "Checking to see if " + username + " is a registered user");
        User user = getUser(username, password);
        if (user != null) {
            log(Logbook.INFO, username + " is a registered user");
        } else {
            log(Logbook.INFO, username + " is not a registered user");
        }
        return user != null;
    }


    public boolean registerUser(String username, String password) {
        log(Logbook.INFO, "Attempting to register user: " + username);
        boolean successful = true;
        if (userExists(username)) {
            successful = false;
            log(Logbook.WARNING, username + " is already an existing user");
            //throw new UserAlreadyExistsException("The username '" + username + "' is already in use.");
        }
        List<User> users = getUsersList();
        User user = new User(username);
        user.setPassword(password);
        Task newTask = new Task("New User Orientation");
        newTask.setDueDate("Today");
        user.addTask(newTask);
        if (entityManager == null) {
            log(Logbook.WARNING, "entityManager is null");
        }
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
                    log(Logbook.INFO, username + " is an existing user");
                }
            }
        }
        return userExists;
    }



    private List<User> getUsersList() {
        return usersList;
    }
}
