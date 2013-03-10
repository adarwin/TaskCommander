package com.adarwin.csc435;

import com.adarwin.logging.Logbook;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

public class TaskManagementServlet extends HttpServlet
{
  private Logbook logbook = new Logbook("../logs/TaskManagementServlet.log");
}
