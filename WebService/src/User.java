package com.adarwin.csc435;

//import com.adarwin.logging.Logbook;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.ElementCollection;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Column;

@Entity
@NamedQueries({
    @NamedQuery(
        name = "getAllUsers",
        query = "SELECT u FROM User u"
    ),
    @NamedQuery(
        name = "getUser",
        query = "SELECT u FROM User u " +
                "WHERE u.username LIKE :username and u.password LIKE :password"
    )
})
public class User implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final String logHeader = "User";
    @Id
    @GeneratedValue
    //@Column(name="USER_ID", nullable=false)
    private Long id;
    //private Logbook logbook = new Logbook("../logs/User.log");
    @NotNull
    private String username;
    @NotNull
    private String password;
    //@ElementCollection(fetch=FetchType.EAGER)
    @OneToMany(orphanRemoval=true, cascade=CascadeType.ALL, fetch=FetchType.EAGER)//, mappedBy="user")
    @JoinColumn(name="USER_ID")
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
      setId(user.getId());
      setUsername(user.getUsername());
      setPassword(user.getPassword());
      setTasks(user.getTasks());
      setCurrentTaskName(user.getCurrentTaskName());
      setCurrentTaskDueDate(user.getCurrentTaskDueDate());
      setLoggedIn(user.getLoggedIn());
  }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }
  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }
  public List<Task> getTasks() { return tasks; }
  public void setTasks(List<Task> tasks) {
      this.tasks.clear();
      //for (Task task : tasks) {
          //if (!this.tasks.contains(task)) {
              Task temp = new Task(task.getName());
              temp.setDueDate(task.getDueDate());
              this.tasks.add(temp);
          //}
      //}
      //this.tasks = tasks;
  }
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
