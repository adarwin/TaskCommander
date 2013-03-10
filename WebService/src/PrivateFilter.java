package com.adarwin.csc435;

import com.adarwin.logging.Logbook;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

//@WebFilter(filterName = "TimeOfDayFilter", urlPatterns = {"/*"}, initParams = { @WebInitParam(name = "mood", value = "awake")})
public class PrivateFilter implements Filter
{
  Logbook logbook;
  FilterConfig filterConfig;
  public void init(FilterConfig filterConfig) throws ServletException
  {
    this.filterConfig = filterConfig;
    logbook = new Logbook("../logs/RequestFilter.log");
  }
  public void destroy()
  {
    filterConfig = null;
    logbook = null;
  }
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
  {
    if (request instanceof HttpServletRequest)
    {
      HttpServletRequest httpRequest = (HttpServletRequest)request;
      //output = ((HttpServletRequest)request).getRequestURI();
      if (!Authentication.isLoggedIn(httpRequest.getSession()))
      {
        if (response instanceof HttpServletResponse)
        {
          logbook.log(Logbook.INFO, "Received request from non-authenticated user and redirected them to /TaskCommander");
          HttpServletResponse httpResponse = (HttpServletResponse)response;
          httpResponse.sendRedirect("/TaskCommander");
        }
        else
        {
          logbook.log(Logbook.WARNING, "Received request from non-authenticated user, but was not able to redirect to /TaskCommander.");
        }
      }
      else
      {
        logbook.log(Logbook.INFO, "Received request from authenticated user and allowed to proceed.");
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
    else
    {
      logbook.log(Logbook.WARNING, "Received request, but was not able to access the session to determine user authenticity.");
    }
  }
}
