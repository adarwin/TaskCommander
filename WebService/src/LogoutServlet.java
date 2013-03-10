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
  Logbook logbook = new Logbook("../logs/LogoutServlet.log");
  @Override
  protected void doPost(HttpServletRequest request,
                       HttpServletResponse response)
                 throws ServletException, IOException
  {
    logbook.log(Logbook.INFO, "Received post request");
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
    logbook.log(Logbook.INFO, "Received get request");
  }
}

