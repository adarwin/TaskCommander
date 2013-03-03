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

@WebServlet("/logout")
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
    //Do logout process
    Cookie[] existingCookies = request.getCookies();
    for (Cookie cookie : existingCookies)
    {
      if (cookie.getName().equals("username"))
      {
        cookie.setValue(null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
      }
      else if (cookie.getName().equals("password"))
      {
        cookie.setValue(null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
      }
    }
    response.sendRedirect("/TaskCommander");
      /*
      String htmlOutput = "<html><head></head><body>";
      htmlOutput += "<p>request.getServletPath() = " + request.getServletPath() + "</p>";
      htmlOutput += "</body></html>";
      PrintWriter out = response.getWriter();
      out.println(htmlOutput);
      */
  }
}

