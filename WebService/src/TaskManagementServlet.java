package com.adarwin.csc435;

import com.adarwin.logging.Logbook;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;

public class TaskManagementServlet extends HttpServlet
{
  private Logbook logbook = new Logbook("../logs/TaskCommander.log");
  private static final long serialVersionUID = 1L;
  private final String logHeader = "TaskManagementServlet";


  private void log(Exception ex)
  {
    logbook.log(logHeader, ex);
  }
  private void log(String level, String message)
  {
    logbook.log(level, logHeader, message);
  }



  @Override
  protected void doGet (HttpServletRequest request,
                        HttpServletResponse response)
                   throws ServletException, IOException
  {
    log(Logbook.INFO, "Received get request. Call doPost(...)");
    doPost(request, response);
  }
  @Override
  protected void doPost (HttpServletRequest request,
                         HttpServletResponse response)
                   throws ServletException, IOException
  {
    log(Logbook.INFO, "Received post request");
    User user = (User)(request.getSession().getAttribute("user"));
    log(Logbook.INFO, "Got user object from session");
    if (user != null)
    {
      log(Logbook.INFO, "The user stored in the session has username: " +
                        user.getUsername());
      if (request.getParameter("addTask") != null)
      {
        Task newTask = new Task(request.getParameter("newTaskName"));
        newTask.setDueDate(request.getParameter("newTaskDueDate"));
        user.addTask(newTask);
      }
      else if (request.getParameter("deleteTask") != null)
      {
        String taskNameToDelete = request.getParameter("taskName");
        user.removeTask(taskNameToDelete);
      }
      RequestDispatcher dispatcher = request.getRequestDispatcher("/private/home.jsp");
      dispatcher.forward(request, response);
    }
    else
    {
      log(Logbook.INFO, "User object was null");
      user.getUsername();
    }
  }
}
