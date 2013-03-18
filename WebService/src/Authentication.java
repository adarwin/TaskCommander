package com.adarwin.csc435;

import com.adarwin.logging.Logbook;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class Authentication
{
  private static Logbook logbook = new Logbook("../logs/Authentication.log");
  //private static HashMap<String, String> registeredUsers;
  //private static List<String> loggedInUsers = new ArrayList<String>();
  protected static String loggedIn = "LoggedIn";

  /*
  private static void initializeUsers()
  {
    registeredUsers = new HashMap<String, String>();
    registeredUsers.put("darwin", "vermont");
    registeredUsers.put("alex", "gaming");
  }
  */



  protected static boolean isRegisteredUser(ServletContext servletContext,
                                            String username, String password)
  {
    return DataTier.isRegisteredUser(servletContext, username, password);
    /*
    if (registeredUsers == null) initializeUsers();
    return registeredUsers.containsKey(username) && registeredUsers.get(username).equals(password);
    */
  }



  protected static boolean isLoggedIn(HttpSession session)
  {
    Object loggedInObject = session.getAttribute(loggedIn);
    boolean isLoggedIn = false;
    if (loggedInObject instanceof Boolean)
    {
      isLoggedIn = (boolean)loggedInObject;
    }
    return isLoggedIn;
  }



  protected static void logUserIn(HttpSession session, String username, String password)
  {
    User user = DataTier.getUser(session.getServletContext(), username, password);
    if (user != null)
    {
      session.setAttribute(loggedIn, true);
      session.setAttribute("user", user);
      logbook.log(Logbook.INFO, "User: '" + user.getUsername() + "' successfully logged in.");
    }
    else
    {
      logbook.log(Logbook.ERROR, "User was null");
    }
  }



  protected static void logUserOut(HttpSession session)
  {
    session.setAttribute(loggedIn, false);
    logbook.log(Logbook.INFO, "User: '" + session.getId() + "' successfully logged out.");
  }
}
