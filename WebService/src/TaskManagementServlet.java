package com.adarwin.csc435;

import com.adarwin.logging.Logbook;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;

public class TaskManagementServlet extends HttpServlet
{
  private Logbook logbook = new Logbook("../logs/TaskManagementServlet.log");
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet (HttpServletRequest request,
                        HttpServletResponse response)
                   throws ServletException, IOException
  {
    doPost(request, response);
  }
  @Override
  protected void doPost (HttpServletRequest request,
                         HttpServletResponse response)
                   throws ServletException, IOException
  {
    logbook.log(Logbook.INFO, "Received post request");
    User user = (User)(request.getSession().getAttribute("user"));
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
}
