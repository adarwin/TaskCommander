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

//@WebServlet("/")
public class Portal extends HttpServlet
{
  Logbook logbook = new Logbook("../logs/Portal.log");
  @Override
  protected void doGet (HttpServletRequest request,
                        HttpServletResponse response)
                 throws ServletException, IOException
  {
    logbook.log(Logbook.INFO, "Received get request");
    HttpSession session = request.getSession();
    if (Authentication.isLoggedIn(request.getSession().getId()))
    {
      logbook.log(Logbook.INFO, "Determined get request was from logged-in user. Redirect to home.jsp.");
      response.sendRedirect("/TaskCommander/private/home.jsp");
      /*
      RequestDispatcher dispatcher = request.getRequestDispatcher("/TaskCommander/private/home.html");
      dispatcher.forward(request, response);
      */
    }
    else
    {
      logbook.log(Logbook.INFO, "Determined get request was not from a logged-in user. Redirect to login.");
      response.sendRedirect("/TaskCommander/login");
      //RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
      //dispatcher.forward(request, response);
    }
  }

  @Override
  protected void doPost (HttpServletRequest request,
                         HttpServletResponse response)
                 throws ServletException, IOException
  {
    logbook.log(Logbook.INFO, "Received post request");
  }

  /*
  private boolean hasValidCookie(HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    Cookie[] existingCookies = request.getCookies();
    boolean usernameCookieExists = false;
    boolean passwordCookieExists = false;
    for (Cookie cookie : existingCookies)
    {
      if (cookie.getName().equals("username") && cookie.getMaxAge() > 0)
      {
        usernameCookieExists = true;
        session.setAttribute("username", cookie.getValue());
      }
      else if (cookie.getName().equals("password") && cookie.getMaxAge() > 0)
      {
        passwordCookieExists = true;
        session.setAttribute("password", cookie.getValue());
      }
    }
    return usernameCookieExists && passwordCookieExists;
  }
  */


}
