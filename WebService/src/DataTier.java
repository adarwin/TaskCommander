package com.adarwin.csc435;

import com.adarwin.logging.Logbook;
import java.util.List;
import java.util.ArrayList;

class DataTier
{
  private static Logbook logbook = new Logbook("../logs/DataTier.log");
  private static List<User> registeredUsers;
  //private static HashMap<String, String> registeredUsers;
  private static List<String> loggedInUsers = new ArrayList<String>();
  protected static String loggedIn = "LoggedIn";

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

  protected static void registerUser(String username, String password) throws UserAlreadyExistsException
  {
    if (userExists(username))
    {
      throw new UserAlreadyExistsException("The username '" + username + "' is already in use.");
    }
    User user = new User(username);
    user.setPassword(password);
    user.addTask(new Task("Sign instantiation certificate"));
    registeredUsers.add(user);
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
