package com.adarwin.csc435;

import com.adarwin.logging.Logbook;
import com.adarwin.csc435.User;
import com.adarwin.csc435.Task;
import com.adarwin.csc435.ejb.RTMConnection;
import com.adarwin.csc435.ejb.RTMConnectionBean;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import javax.ejb.EJB;

public class TaskManagementServlet extends HttpServlet implements CustomizedLogger {

  private Logbook logbook = new Logbook("../logs/TaskCommander.log");
  private static final long serialVersionUID = 1L;
  private final String logHeader = "TaskManagementServlet";

  private InitialContext initialContext;
  private RTMConnection rtmConnectionBean;


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
        rtmConnectionBean = (RTMConnection)initialContext.lookup("java:app/ejb/RTMConnectionBean");
      } catch (NamingException ex) {
        log(ex);
        if (rtmConnectionBean == null) {
            log(Logbook.WARNING, "RTMConnectionBean is null and therefore " +
                                 "can't be used for anything.");
        }
      }
  }
  @Override
  protected void doGet (HttpServletRequest request,
                        HttpServletResponse response)
                   throws ServletException, IOException
  {
    log(Logbook.INFO, "Received get request");
    HttpSession session = request.getSession();
    if (request.getParameter("rtmLogin") != null) {
        String url = rtmConnectionBean.getAuthenticationURL();
        session.setAttribute("rtmAuthInProgress", true);
        /*
        String htmlOutput = "<html><head></head><body onload='newWindow()'>";
        htmlOutput += "<script><function newWindow()>";
        htmlOutput += "window.open('" + url + "')";
        htmlOutput += "</function>";
        htmlOutput += "</body></html>";
        PrintWriter out = response.getWriter();
        out.println(htmlOutput);
        */
        response.sendRedirect(url);
    }
    //response.sendRedirect("/TaskCommander");
    //doPost(request, response);
  }
  @Override
  protected void doPost (HttpServletRequest request,
                         HttpServletResponse response)
                   throws ServletException, IOException
  {
    log(Logbook.INFO, "Received post request");
    HttpSession session = request.getSession();
    User user = (User)(session.getAttribute("user"));
    boolean rtmAuthInProgress = (boolean)(session.getAttribute("rtmAuthInProgress"));
    if (rtmAuthInProgress) {
        // Finish rtm authorization process
        session.setAttribute("rtmToken", rtmConnectionBean.getToken());
        session.setAttribute("rtmAuthInProgress", false);
        session.setAttribute("rtmLoggedIn", true);
    }
    boolean rtmLoggedIn = (boolean)(session.getAttribute("rtmLoggedIn"));
    log(Logbook.INFO, "Got user :" + user + " from session");
    if (user != null)
    {
      log(Logbook.INFO, "The user stored in the session has username: " +
                        user.getUsername());
      if (request.getParameter("addTask") != null)
      {
        String newTaskName = request.getParameter("newTaskName");
        String newTaskDueDate = request.getParameter("newTaskDueDate");
        if (newTaskName != null && !newTaskName.equals(""))
        {
          Task newTask = new Task(newTaskName);
          newTask.setDueDate(newTaskDueDate);
          user.addTask(newTask);
          user.setCurrentTaskName("");
          user.setCurrentTaskDueDate("");
          System.out.println("Modifying User: " + user);
          if (rtmLoggedIn) {
              // Add task to rtm
              rtmConnectionBean.addTask(newTaskName);
          }
        }
        else
        {
          user.setCurrentTaskName("Specify a task name");
        }
      }
      else if (request.getParameter("deleteTask") != null)
      {
        String taskNameToDelete = request.getParameter("taskName");
        user.removeTask(taskNameToDelete);
        user.setCurrentTaskName("");
        user.setCurrentTaskDueDate("");
      }
      else if (request.getParameter("editTask") != null)
      {
        String taskNameToEdit = request.getParameter("taskName");
        String taskDueDateToEdit = request.getParameter("taskDueDate");
        user.setCurrentTaskName(taskNameToEdit);
        user.setCurrentTaskDueDate(taskDueDateToEdit);
        user.removeTask(taskNameToEdit);
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
