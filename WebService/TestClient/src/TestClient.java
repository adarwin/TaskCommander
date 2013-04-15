package com.adarwin.csc435.test;

import com.adarwin.csc435.ejb.Authentication;
import com.adarwin.csc435.User;
import com.adarwin.csc435.Task;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TestClient {
    public static void main(String[] args) {
        System.out.println("Started Main Method");
        Authentication authenticationBean = null;
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("jndi.properties"));
            InitialContext initialContext = new InitialContext();
            authenticationBean = (Authentication)initialContext.lookup("java:global/TaskCommander/ejb/AuthenticationBean");
            System.out.println("Created Authentication Bean");
            //Authentication authenticationBean = (Authentication)initialContext.lookup("AuthenticationBean");
        } catch (NamingException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String username = "";
        String password = "";
        String command = "";
        User user = null;
        while (!command.equals("quit")) {
            try {
                if (command.equals("login")) {
                    System.out.print("Username: ");
                    username = reader.readLine();
                    System.out.print("Password: ");
                    password = reader.readLine();
                    user = authenticationBean.logUserIn(username, password);
                    if (user == null) {
                        System.out.println("Failed to log " + username + " in");
                    } else {
                        System.out.println("Successfully logged in: " + user.getUsername());
                    }
                } else if (command.equals("logout")) {
                    authenticationBean.logUserOut(user);
                    user = null;
                    System.out.println("Logged Out");
                } else if (command.equals("isRegistered")) {
                    System.out.print("Username: ");
                    username = reader.readLine();
                    System.out.print("Password: ");
                    password = reader.readLine();
                    System.out.println(authenticationBean.isRegisteredUser(username, password));
                } else if (command.equals("register")) {
                    System.out.print("Username: ");
                    username = reader.readLine();
                    System.out.print("Password: ");
                    password = reader.readLine();
                    System.out.println(authenticationBean.registerUser(username, password));
                } else if (command.equals("loggedIn")) {
                    System.out.println(authenticationBean.isLoggedIn(user));
                } else if (command.equals("tasks")) {
                    if (user != null) {
                        List<Task> tasks = user.getTasks();
                        for (Task task : tasks) {
                            System.out.println("Name: " + task.getName() + ", DueDate: " + task.getDueDate());
                        }
                    } else {
                        System.out.println("User object is null");
                    }
                } else if (command.equals("addTask")) {
                    if (user != null) {
                        List<Task> tasks = user.getTasks();
                        tasks.add(new Task("NewTaskName"));
                    } else {
                        System.out.println("User object is null");
                    }
                }
                System.out.print("Enter Command: ");
                command = reader.readLine();
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }
        }
    }
}
