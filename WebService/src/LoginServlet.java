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

//@WebServlet("/login")
public class LoginServlet extends HttpServlet
{

  Logbook logbook = new Logbook("../logs/LoginServlet.log");




  @Override
  protected void doGet (HttpServletRequest request,
                     HttpServletResponse response)
              throws ServletException, IOException
  {
    logbook.log(Logbook.INFO, "Received get request");
    //Check to see if the user is already logged in
    if (isLoggedInUser(request))
    {
      logbook.log(Logbook.INFO, "Determined get request was from logged-in user. Forward to home.html.");
      RequestDispatcher dispatcher = request.getRequestDispatcher("/home.html");
      dispatcher.forward(request, response);
    }
    else
    {
      response.sendRedirect("/TaskCommander/login.jsp");
    }
  }





  @Override
  protected void doPost (HttpServletRequest request,
                      HttpServletResponse response)
              throws ServletException, IOException
  {
    logbook.log(Logbook.INFO, "Received post request");
    if (true)//authenticateUser(request))
    {
      //Check against database
      if (true)
      {
        HttpSession session = request.getSession();
        updateCookies(request, response);
        session.setAttribute("username", request.getParameter("username"));
        session.setAttribute("password", request.getParameter("password"));
        response.sendRedirect("/TaskCommander/home.html");
        /*
        RequestDispatcher dispatcher = request.getRequestDispatcher("/home.jsp");
        dispatcher.forward(request, response);
        */
        /*
        String htmlOutput = "<html><head></head><body>";
        htmlOutput += "<p>session.getAttribute(\"username\") = " + session.getAttribute("username") + "</p>";
        htmlOutput += "</body></html>";
        PrintWriter out = response.getWriter();
        out.println(htmlOutput);
        */
        /*
        htmlOutput += "<h1>Welcome, " + username + "</h1>";
        htmlOutput += "<p>Username = " + username + "</p>";
        htmlOutput += "<p>Password = " + password + "</p>";
        */
      }
      else
      {
        String htmlOutput = "<html><head></head><body>";
        htmlOutput += "<h2>Invalid login credentials</h2>";
        htmlOutput += "</body></html>";
        PrintWriter out = response.getWriter();
        out.println(htmlOutput);
      }
    }
  }





  static protected boolean isLoggedInUser(HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    Cookie[] existingCookies = request.getCookies();
    boolean validUsername = false;
    boolean validPassword = false;
    if (existingCookies != null)
    {
      for (Cookie cookie : existingCookies)
      {
        if (cookie.getName().equals("username") && cookie.getValue().equals("darwin"))
        {
          validUsername = true;
        }
        else if (cookie.getName().equals("password") && cookie.getValue().equals("pass"))
        {
          validPassword = true;
        }
      }
    }
    return validUsername && validPassword;
  }






  private boolean authenticateUser(HttpServletRequest request)
  {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    return (username.equals("darwin") && password.equals("pass")) || isLoggedInUser(request);
  }







  private void updateCookies(HttpServletRequest request, HttpServletResponse response)
  {
    Cookie[] existingCookies = request.getCookies();
    for (Cookie cookie : existingCookies)
    {
      if (cookie.getName().equals("username"))
      {
        cookie.setValue(request.getParameter("username"));
        response.addCookie(cookie);
      }
      else if (cookie.getName().equals("password"))
      {
        cookie.setValue(request.getParameter("password"));
        response.addCookie(cookie);
      }
    }
  }
}
