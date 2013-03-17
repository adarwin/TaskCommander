package com.adarwin.csc435;

import com.adarwin.logging.Logbook;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

public class LoggingFilter implements Filter
{
  Logbook logbook;
  FilterConfig filterConfig;

  public void init(FilterConfig filterConfig) throws ServletException
  {
    this.filterConfig = filterConfig;
    logbook = new Logbook("../logs/TaskCommander.log");
  }

  public void destroy()
  {
    logbook = null;
    filterConfig = null;
  }

  public void doFilter(ServletRequest request, ServletResponse response,
                       FilterChain chain) throws IOException, ServletException
  {
    logbook.log(Logbook.INFO, "LoggingFilter detected ServletRequest");
    if (chain == null)
    {
      logbook.log(Logbook.WARNING, "Filter chain == null");
    }
    else
    {
      chain.doFilter(request, response);
    }
  }
}
