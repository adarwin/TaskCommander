package com.adarwin.csc435;

//import com.adarwin.logging.Logbook;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.ElementCollection;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.persistence.OneToMany;
import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class User implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final String logHeader = "User";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    //private Logbook logbook = new Logbook("../logs/User.log");
    @NotNull
    private String username;
    @NotNull
    private String password;
    @ElementCollection
    //@OneToMany(cascade=REMOVE, mappedBy="user")
    private List<Task> tasks;
    private String currentTaskName;
    private String currentTaskDueDate;
    private boolean loggedIn = false;



  /*
  private void log(Exception ex)
  {
    logbook.log(logHeader, ex);
  }
  private void log(String level, String message)
  {
    logbook.log(level, logHeader, message);
  }
  */




  public User()
  {
    tasks = new ArrayList<Task>();
  }
  public User(String username)
  {
    this();
    this.username = username;
  }


  public void updateFrom(User user) {
      setUsername(user.getUsername());
      setPassword(user.getPassword());
      setTasks(user.getTasks());
      setCurrentTaskName(user.getCurrentTaskName());
      setCurrentTaskDueDate(user.getCurrentTaskDueDate());
      setLoggedIn(user.getLoggedIn());
  }

  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }
  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }
  public List<Task> getTasks() { return tasks; }
  public void setTasks(List<Task> tasks) { this.tasks = tasks; }
  public String getCurrentTaskName() { return currentTaskName; }
  public void setCurrentTaskName(String taskName) { currentTaskName = taskName; }
  public String getCurrentTaskDueDate() { return currentTaskDueDate; }
  public void setCurrentTaskDueDate(String dueDate) { currentTaskDueDate = dueDate; }
  public boolean getLoggedIn() { return loggedIn; }
  public void setLoggedIn(boolean loggedIn) { this.loggedIn = loggedIn; }

  public void addTask(Task task)
  {
    if (tasks == null)
    {
      tasks = new ArrayList<Task>();
    }
    if (!tasks.contains(task))
    {
      tasks.add(task);
      //log(Logbook.INFO, "Added task: '" + task.getName() + "'");
    }
    else
    {
      //log(Logbook.WARNING, "Task: '" + task.getName() + "' already exists and was therefore not added");
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
