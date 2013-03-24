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

public class LoginServlet extends HttpServlet
{

  private Logbook logbook = new Logbook("../logs/TaskCommander.log");
  private static final long serialVersionUID = 1L;
  private final String logHeader = "LoginServlet";


  private void log(Exception ex)
  {
    logbook.log(logHeader, ex);
  }
  private void log(String level, String message)
  {
    logbook.log(level, logHeader, message);
  }




  @Override
  protected void doGet (HttpServletRequest request,
                     HttpServletResponse response)
              throws ServletException, IOException
  {
    log(Logbook.INFO, "Received get request");
    //Check to see if the user is already logged in
    if (Authentication.isLoggedIn(request.getSession()))
    {
      log(Logbook.INFO, "Determined get request was from logged-in user. Forward to home.jsp.");
      RequestDispatcher dispatcher = request.getRequestDispatcher("/private/home.jsp");
      dispatcher.forward(request, response);
    }
    else
    {
      log(Logbook.INFO, "Determed get request was not from a logged-in user. Redirect to login.jsp");
      RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
      dispatcher.forward(request, response);
      //response.sendRedirect("/TaskCommander/login.jsp");
    }
  }





  @Override
  protected void doPost (HttpServletRequest request,
                         HttpServletResponse response)
                   throws ServletException, IOException
  {
    log(Logbook.INFO, "Received post request");
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    // First check to make sure the input values are not malicious
    if (!areMaliciousInputs(username, password))
    {
      if (request.getParameter("login") != null
          && Authentication.isRegisteredUser(getServletContext(), username, password))
      {
        log(Logbook.INFO, "Post request is from valid registered user, '" + username + "'");
        HttpSession session = request.getSession();
        Authentication.logUserIn(session, username, password);
        log(Logbook.INFO, "Logged " + username + " in");
        response.sendRedirect("/TaskCommander/private/home.jsp");
      }
      else if (request.getParameter("register") != null)
      {
        log(Logbook.INFO, "Post request is from new user, '" + username + "'");
        HttpSession session = request.getSession();
        try
        {
          DataTier.registerUser(request.getServletContext(), username, password);
          RequestDispatcher dispatcher = request.getRequestDispatcher("/registration.jsp");
          dispatcher.forward(request, response);
        }
        catch (UserAlreadyExistsException ex)
        {
          log(ex);
          String htmlOutput = "<html><head></head><body>";
          htmlOutput += "<h2>Username Already Taken!</h2>";
          htmlOutput += "<form action=\"/TaskCommander\" method=\"get\" name=\"back\">";
          htmlOutput += "<input name=\"Back\" type=\"submit\" value=\"Back\">";
          htmlOutput += "</form>";
          htmlOutput += "</body></html>";
          PrintWriter out = response.getWriter();
          out.println(htmlOutput);
        }
        //response.sendRedirect("/TaskCommander/registration.jsp");
      }
      else
      {
        String htmlOutput = "<html><head></head><body>";
        htmlOutput += "<h2>Invalid login credentials</h2>";
        htmlOutput += "<form action=\"/TaskCommander\" method=\"get\" name=\"back\">";
        htmlOutput += "<input name=\"Back\" type=\"submit\" value=\"Back\">";
        htmlOutput += "</form>";
        htmlOutput += "</body></html>";
        PrintWriter out = response.getWriter();
        out.println(htmlOutput);
      }
    }
    else
    {
      /*
       * Don't need to check the cast here because it is already covered by the
       * check of the request
       */
      log(Logbook.WARNING, "Detected malicious login credentials input");
      RequestDispatcher dispatcher = request.getRequestDispatcher("/malicious.html");
      dispatcher.forward(request, response);
    }
    //Check against database and authenticate user
  }



  private boolean areMaliciousInputs(String username, String password)
  {
    boolean maliciousInputs = false;
    try
    {
      assertNotNull(username);
      assertNotNull(password);
      assertNotEmpty(username);
      assertNotEmpty(password);
      assertNotHTML(username);
      assertNotHTML(password);
    }
    catch (MaliciousInputException ex)
    {
      log(ex);
      maliciousInputs = true;
    }
    return maliciousInputs;
  }
  private void assertNotNull(String input) throws MaliciousInputException
  {
    if (input == null)
      throw new MaliciousInputException("Input was null");
  }
  private void assertNotEmpty(String input) throws MaliciousInputException
  {
    if (input.equals(""))
      throw new MaliciousInputException("Input was empty");
  }
  private void assertNotHTML(String input) throws MaliciousInputException
  {
    String[] illegalStrings = new String[] {"<", ">", ";", "/", "\\"};
    for(String string : illegalStrings)
    {
      if (input.contains(string))
        throw new MaliciousInputException("Illegal Content");
    }
  }


}
