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

@WebServlet("/login")
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
    response.sendRedirect("/TaskCommander/login.jsp");
  }

  private boolean authenticateUser(HttpServletRequest request)
  {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    return (username.equals("darwin") && password.equals("pass")) || isLoggedInUser(request);
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

  @Override
  protected void doPost (HttpServletRequest request,
                      HttpServletResponse response)
              throws ServletException, IOException
  {
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

  private boolean isValidUser(String username, String password)
  {
    //Check users database
    return true;
  }
}
