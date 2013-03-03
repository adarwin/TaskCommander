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

@WebServlet("/")
public class HomeServlet extends HttpServlet
{
  Logbook logbook = new Logbook("../logs/HomeServlet.log");
  @Override
  protected void doGet (HttpServletRequest request,
                        HttpServletResponse response)
                 throws ServletException, IOException
  {
    logbook.log(Logbook.INFO, "Received get request");
    HttpSession session = request.getSession();
    if (LoginServlet.isLoggedInUser(request))
    {
      RequestDispatcher dispatcher = request.getRequestDispatcher("/home.html");
      dispatcher.forward(request, response);
      //response.sendRedirect("/TaskCommander/home.html");
    }
    else
    {
      RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
      dispatcher.forward(request, response);
      //response.sendRedirect("/TaskCommander/login.jsp");
    }
    /*
    if (false)
    {
      response.sendRedirect("/TaskCommander/home.html");
    }
    else
    {
      response.sendRedirect("/TaskCommander/login.jsp");
    }
    */
  }

  @Override
  protected void doPost (HttpServletRequest request,
                         HttpServletResponse response)
                 throws ServletException, IOException
  {
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
