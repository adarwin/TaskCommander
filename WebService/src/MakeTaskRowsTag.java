package com.adarwin.csc435;

import com.adarwin.logging.Logbook;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.*;

public class MakeTaskRowsTag extends SimpleTagSupport
{
  private Logbook logbook = new Logbook("../logs/MakeTaskRowsTag.log");
  private User user;
  private Task currentTask;
  private String taskNameKey;
  private String taskDueDateKey;


  private void log(Exception ex)
  {
    logbook.log(ex);
  }
  private void log(String level, String message)
  {
    logbook.log(level, "MakeTaskRowsTag: " + message);
  }


  public void setUser(User user)
  {
    this.user = user;
    log(Logbook.INFO, "Set user to " + user.getUsername());
  }
  public void setTaskNameKey(String taskNameKey)
  {
    this.taskNameKey = taskNameKey;
    log(Logbook.INFO, "Set taskNameKey to '" + taskNameKey + "'");
  }
  public void setTaskDueDateKey(String taskDueDateKey)
  {
    this.taskDueDateKey = taskDueDateKey;
    log(Logbook.INFO, "Set taskDueDateKey to '" + taskDueDateKey + "'");
  }


  public void doTag() throws JspException, IOException
  {
    JspContext context = getJspContext();
    JspWriter jspOut = context.getOut();
    JspFragment body = getJspBody();
    //jspOut.print("testString");
    StringWriter writer = new StringWriter();
    body.invoke(writer);
    String outputTemplate = writer.toString();
    log(Logbook.INFO, "OutputTemplate = " + outputTemplate);
    String workingCopy = outputTemplate;
    String output = "";
    if (user.getTasks() == null)
    {
      log(Logbook.ERROR, "user's task list is null");
    }
    for (Task task : user.getTasks())
    {
      workingCopy = outputTemplate;
      workingCopy = workingCopy.replace(taskNameKey, task.getName());
      workingCopy = workingCopy.replace(taskDueDateKey, task.getDueDate());
      output += workingCopy;
    }
    log(Logbook.INFO, output);
    jspOut.print(output);
  }
}
