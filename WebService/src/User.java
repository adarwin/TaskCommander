package com.adarwin.csc435;

import com.adarwin.logging.Logbook;
import java.util.List;
import java.util.ArrayList;

public class User
{
  private Logbook logbook = new Logbook("../logs/User.log");
  private String username;
  private String password;
  private List<Task> tasks;
  private final String logHeader = "User";



  private void log(Exception ex)
  {
    logbook.log(logHeader, ex);
  }
  private void log(String level, String message)
  {
    logbook.log(level, logHeader, message);
  }




  public User()
  {
    tasks = new ArrayList<Task>();
  }
  public User(String username)
  {
    this();
    this.username = username;
  }
  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }
  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }
  public List<Task> getTasks() { return tasks; }
  public void setTasks(List<Task> tasks) { this.tasks = tasks; }

  public void addTask(Task task)
  {
    if (tasks == null)
    {
      tasks = new ArrayList<Task>();
    }
    if (!tasks.contains(task))
    {
      tasks.add(task);
      log(Logbook.INFO, "Added task: '" + task.getName() + "'");
    }
    else
    {
      log(Logbook.WARNING, "Task: '" + task.getName() + "' already exists and was therefore not added");
    }
  }
  public void removeTask(String taskName)
  {
    if (tasks != null)
    {
      Task taskToRemove = null;
      for (Task task : tasks)
      {
        if (task.getName().equals(taskName))
        {
          taskToRemove = task;
          break;
        }
      }
      tasks.remove(taskToRemove);
    }
  }
}
