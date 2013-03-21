package com.adarwin.csc435;

import com.adarwin.logging.Logbook;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.ServletContext;

class DataTier
{
  private static Logbook logbook = new Logbook("../logs/TaskCommander.log");
  private static List<User> registeredUsers;
  //private static String registeredUsers = "RegisteredUsers";
  //private static HashMap<String, String> registeredUsers;
  private static List<String> loggedInUsers = new ArrayList<String>();
  protected static String loggedIn = "LoggedIn";


  private void log(Exception ex)
  {
    logbook.log(ex);
  }
  private void log(String level, String message)
  {
    logbook.log(level, "DataTier: " + message);
  }

  DataTier()
  {
    registeredUsers = new ArrayList<User>();
  }


  protected static User getUser(String username, String password)
  {
    User outputUser = null;
    for (User user : registeredUsers)
    {
      if (user.getUsername().equals(username) && user.getPassword().equals(password))
      {
        outputUser = user;
      }
    }
    return outputUser;
  }
  protected static boolean isRegisteredUser(User user)
  {
    boolean registered = false;
    if (registeredUsers.contains(user))
    {
      registered = true;
    }
    return registered;
  }

  protected static boolean isRegisteredUser(String username, String password)
  {
    boolean registered = false;
    if (registeredUsers == null)
      registeredUsers = new ArrayList<User>();
    for (User user : registeredUsers)
    {
      if (user.getUsername().equals(username) && user.getPassword().equals(password))
      {
        registered = true;
      }
    }
    return registered;
  }

  protected static void registerUser(ServletContext servletContext,
                                     String username, String password)
                          throws UserAlreadyExistsException
  {
    if (userExists(username))
    {
      throw new UserAlreadyExistsException("The username '" + username + "' is already in use.");
    }
    //List<User> userList = servletContext.getAttribute(registeredUsers);
    User user = new User(username);
    user.setPassword(password);
    Task newTask = new Task("New User Orientation");
    newTask.setDueDate("Today");
    user.addTask(newTask);
    registeredUsers.add(user);
    //userList.add(user);
    //servletContext.setAttribute(registeredUsers, userList);
  }
  protected static boolean userExists(String username)
  {
    boolean userExists = false;
    if (registeredUsers == null)
      registeredUsers = new ArrayList<User>();
    for (User user : registeredUsers)
    {
      if (user.getUsername().equals(username))
      {
        userExists = true;
      }
    }
    return userExists;
  }
}
