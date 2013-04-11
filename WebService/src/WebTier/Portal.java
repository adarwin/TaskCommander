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

public class Portal extends HttpServlet implements CustomizedLogger {

  private Logbook logbook = new Logbook("../logs/TaskCommander.log");
  private static final long serialVersionUID = 1L;
  private final String logHeader = "Portal";
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
  public void init() { // don't need to call super.init() because this is a
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
  protected void doGet (HttpServletRequest request,
                        HttpServletResponse response)
                 throws ServletException, IOException
  {
    log(Logbook.INFO, "Received get request: " + request.getRequestURI());
    HttpSession session = request.getSession();
    User user = (User)(request.getSession().getAttribute("user"));
    if (authenticationBean.isLoggedIn(user)) {
      log(Logbook.INFO, "Determined get request was from logged-in user. Redirect to home.jsp.");
      response.sendRedirect("/TaskCommander/private/home.jsp");
    }
    else {
      log(Logbook.INFO, "Determined get request was not from a logged-in user. "
                        + "Redirect to login.");
      response.sendRedirect("/TaskCommander/login");
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
