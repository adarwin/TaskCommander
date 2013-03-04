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
  private static HashMap<String, String> registeredUsers;
  private static List<String> loggedInUsers = new ArrayList<String>();

  private static void initializeUsers()
  {
    registeredUsers = new HashMap<String, String>();
    registeredUsers.put("darwin", "vermont");
    registeredUsers.put("alex", "professor");
  }



  protected static boolean isRegisteredUser(String username, String password)
  {
    if (registeredUsers == null) initializeUsers();
    return registeredUsers.containsKey(username) && registeredUsers.get(username).equals(password);
  }



  protected static boolean isLoggedIn(String sessionID)
  {
    return loggedInUsers.contains(sessionID);
  }



  protected static void logUserIn(String sessionID)
  {
    loggedInUsers.add(sessionID);
  }



  protected static void logUserOut(String sessionID)
  {
    loggedInUsers.remove(sessionID);
  }
}
