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

public class Portal extends HttpServlet
{

  private Logbook logbook = new Logbook("../logs/TaskCommander.log");
  private static final long serialVersionUID = 1L;

  private void log(Exception ex)
  {
    logbook.log(ex);
  }
  private void log(String level, String message)
  {
    logbook.log(level, "Portal", message);
  }



  @Override
  protected void doGet (HttpServletRequest request,
                        HttpServletResponse response)
                 throws ServletException, IOException
  {
    log(Logbook.INFO, "Received get request: " + request.getRequestURI());
    HttpSession session = request.getSession();
    if (Authentication.isLoggedIn(request.getSession()))
    {
      log(Logbook.INFO, "Determined get request was from logged-in user. Redirect to home.jsp.");
      response.sendRedirect("/TaskCommander/private/home.jsp");
    }
    else
    {
      log(Logbook.INFO, "Determined get request was not from a logged-in user. "
                        + "Redirect to login.");
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
    log(Logbook.INFO, "Received post request");
  }



}
