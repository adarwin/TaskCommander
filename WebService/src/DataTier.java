package com.adarwin.csc435;

import com.adarwin.logging.Logbook;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.ServletContext;

class DataTier
{
  private static Logbook logbook = new Logbook("../logs/TaskCommander.log");
  //private static List<User> registeredUsers;
  private static final String registeredUsers = "RegisteredUsers";
  //private static HashMap<String, String> registeredUsers;
  //private static List<String> loggedInUsers = new ArrayList<String>();
  //private static final String loggedInUsers = "LoggedInUsers";
  protected static final String loggedIn = "LoggedIn";


  protected static User getUser(ServletContext servletContext, String username, String password)
  {
    User outputUser = null;
    List<User> users = getUsersList(servletContext);
    for (User user : users)
    {
      if (user.getUsername().equals(username) && user.getPassword().equals(password))
      {
        outputUser = user;
      }
    }
    return outputUser;
  }



  protected static boolean isRegisteredUser(ServletContext servletContext, User user)
  {
    boolean registered = false;
    List<User> users = getUsersList(servletContext);
    if (users.contains(user))
    {
      registered = true;
    }
    return registered;
  }



  protected static boolean isRegisteredUser(ServletContext servletContext,
                                            String username, String password)
  {
    boolean registered = false;
    List<User> users = getUsersList(servletContext);
    for (User user : users)
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
    if (userExists(servletContext, username))
    {
      throw new UserAlreadyExistsException("The username '" + username + "' is already in use.");
    }
    List<User> userList = getUsersList(servletContext);
    User user = new User(username);
    user.setPassword(password);
    Task newTask = new Task("New User Orientation");
    newTask.setDueDate("Today");
    user.addTask(newTask);
    userList.add(user);
    servletContext.setAttribute(registeredUsers, userList);
  }



  protected static boolean userExists(ServletContext servletContext, String username)
  {
    boolean userExists = false;
    List<User> users = getUsersList(servletContext);
    if (users != null)
    {
      for (User user : users)
      {
        if (user.getUsername().equals(username))
        {
          userExists = true;
        }
      }
    }
    return userExists;
  }



  @SuppressWarnings("unchecked")
  private static List<User> getUsersList(ServletContext servletContext)
  {
    Object temp = servletContext.getAttribute(registeredUsers);
    logbook.log(Logbook.INFO, "DataTier received object: '" + registeredUsers +
                              "' from servlet context");
    List<User> users = null;
    try
    {
      users = (List<User>)temp;
      logbook.log(Logbook.INFO, "DataTier successfully cast " + registeredUsers
                                + " object to List<User>");
    }
    catch (ClassCastException ex)
    {
      logbook.log(Logbook.ERROR, "ServletContext's attribute, '" +
                                  registeredUsers + "' was not of the " +
                                  "expected type");
      logbook.log(ex);
    }
    if (users == null)
    {
      logbook.log(Logbook.WARNING, "UsersList was found to be null in servlet "
                                   + "context and was therefore initialized to"
                                   + " an empty list.");
      users = new ArrayList<User>();
    }
    return users;
  }



}
