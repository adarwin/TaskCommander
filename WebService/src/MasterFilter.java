package com.adarwin.csc435;

import com.adarwin.logging.Logbook;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.List;
import java.util.ArrayList;

public class MasterFilter implements Filter, CustomizedLogger
{

  private Logbook logbook;
  private FilterConfig filterConfig;
  private List<String> restrictedURIs;
  private final String logHeader = "MasterFilter";



  public void log(Exception ex)
  {
    logbook.log(logHeader, ex);
  }
  public void log(String level, String message)
  {
    logbook.log(level, logHeader, message);
  }




  public void init(FilterConfig filterConfig) throws ServletException
  {
    this.filterConfig = filterConfig;
    logbook = new Logbook("../logs/TaskCommander.log");
    /*
     * Initialize restrictedURIs with all html error pages  and all restricted
     * servlets so that users cannot view such resources on their own accord
     */
    restrictedURIs = new ArrayList<String>();
    restrictedURIs.add("/TaskCommander/error.html");
    restrictedURIs.add("/TaskCommander/error404.html");
    restrictedURIs.add("/TaskCommander/taskmanagement");
    restrictedURIs.add("/TaskCommander/malicious.html");
  }



  public void destroy()
  {
    logbook = null;
    filterConfig = null;
    restrictedURIs = null;
  }



  public void doFilter(ServletRequest request, ServletResponse response,
                       FilterChain chain) throws IOException, ServletException
  {
    log(Logbook.INFO, "Detected ServletRequest");
    if (chain == null)
    {
      log(Logbook.WARNING, "Filter chain == null");
    }
    else
    {
      String requestURI = "";
      if (request instanceof HttpServletRequest)
      {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        requestURI = httpRequest.getRequestURI();
      }
      else
      {
        log(Logbook.WARNING, "ServletRequest could not be cast as "
                             + "HttpServletRequest. Check to make sure that "
                             + "the HTTP protocol is being used.");
      }
      if (restrictedURIs.contains(requestURI))
      {
        if (response instanceof HttpServletResponse)
        {
          HttpServletResponse httpResponse = (HttpServletResponse)response;
          httpResponse.sendRedirect("/TaskCommander");
        }
        else
        {
          log(Logbook.WARNING, "ServletResponse could not be cast as "
                               + "HttpServletResponse. Check to make sure that "
                               + "the HTTP protocol is being used.");
        }
      }
      else
      {
        chain.doFilter(request, response);
      }
    }
  }

}
