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

//@WebServlet("/logout")
public class LogoutServlet extends HttpServlet
{
  private Logbook logbook = new Logbook("../logs/TaskCommander.log");
  private static final long serialVersionUID = 1L;


  private void log(Exception ex)
  {
    logbook.log(ex);
  }
  private void log(String level, String message)
  {
    logbook.log(level, "LogoutServlet", message);
  }



  @Override
  protected void doPost(HttpServletRequest request,
                       HttpServletResponse response)
                 throws ServletException, IOException
  {
    log(Logbook.INFO, "Received post request");
    HttpSession session = request.getSession();
    Authentication.logUserOut(session);
    session.invalidate();
    response.sendRedirect("/TaskCommander");
  }


  @Override
  protected void doGet(HttpServletRequest request,
                       HttpServletResponse response)
                 throws ServletException, IOException
  {
    log(Logbook.INFO, "Received get request");
  }
}

