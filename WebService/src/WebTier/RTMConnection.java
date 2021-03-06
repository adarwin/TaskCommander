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

class RTMConnection extends HttpServlet
{
  private Logbook logbook = new Logbook("../logs/TaskCommander.log");
  private static final long serialVersionUID = 1L;
  private final String logHeader = "RTMConnection";

  private void log(Exception ex)
  {
    logbook.log(logHeader, ex);
  }
  private void log(String level, String message)
  {
    logbook.log(level, logHeader, message);
  }
}
