package com.adarwin.csc435;

import com.adarwin.logging.Logbook;
import com.adarwin.csc435.ejb.AuthenticationBean;
import com.adarwin.csc435.ejb.Authentication;
import java.io.IOException;
import java.io.PrintWriter;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class LogoutServlet extends HttpServlet implements CustomizedLogger {

  private Logbook logbook = new Logbook("../logs/TaskCommander.log");
  private static final long serialVersionUID = 1L;
  private final String logHeader = "LogoutServlet";
  private InitialContext initialContext;
  private Authentication authenticationBean;


  public void log(Exception ex)
  {
    logbook.log(logHeader, ex);
  }
  public void log(String level, String message)
  {
    logbook.log(level, logHeader, message);
  }




  @Override
  public void init() { // Don't need to call super.init() because this is a
                       // convenience method designed to prevent having to do
                       // that.
      try {
        initialContext = new InitialContext();
        authenticationBean = (Authentication)initialContext.lookup("java:app/ejb/AuthenticationBean");
      } catch (NamingException ex) {
        log(ex);
        if (authenticationBean == null) {
            log(Logbook.WARNING, "AuthenticationBean is null and therefore " +
                                 "can't be used for anything.");
        }
      }
  }




  @Override
  protected void doPost(HttpServletRequest request,
                       HttpServletResponse response)
                 throws ServletException, IOException
  {
    log(Logbook.INFO, "Received post request");
    HttpSession session = request.getSession();
    User user = (User)(request.getSession().getAttribute("user"));
    authenticationBean.logUserOut(user);
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

