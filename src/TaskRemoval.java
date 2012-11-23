/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.util.ArrayList;

public class TaskRemoval implements Command
{
  private ArrayList<TaskView> taskViewsToUpdate;
  private Task task;
  private boolean DEBUG = true;
  private String CLASS = "TaskRemoval";

  private void log(String message)
  {
    TaskCommander.log(CLASS, message);
  }

  public TaskRemoval(Task task)
  {
    this.task = task;
    ArrayList<TaskView> taskViewsToUpdate = TaskCommander.getRegisteredTaskViews();
    this.taskViewsToUpdate = taskViewsToUpdate;
  }


  public void run()
  {
    if (DEBUG) log("Attempting to run TaskRemoval");
    ArrayList<Task> tasks = TaskCommander.getTasks();
    if (DEBUG) log("Remove the task contained in this command from the existing tasks list");
    tasks.remove(task);
    if (taskViewsToUpdate != null)
    {
      if (DEBUG) log("Remove task from taskViewToUpdate");
      for (TaskView taskView : taskViewsToUpdate)
      {
        taskView.removeTask(task);
      }
    }
    else
    {
      if (DEBUG) log("Since taskViewToUpdate was null, we did nothing.");
    }
  }
  public void undo()
  {
    if (DEBUG) log("Attempting to undo TaskRemoval");
    if (taskViewsToUpdate != null)
    {
      if (DEBUG) log("Get existing tasks from TaskCommander");
      ArrayList<Task> tasks = TaskCommander.getTasks();
      if (DEBUG) log("Add the task contained in this command to the existing tasks list");
      tasks.add(task);
      if (DEBUG) log("Add the task to the taskViewToUpdate");
      for (TaskView taskView : taskViewsToUpdate)
      {
        taskView.addTask(task);
      }
    }
    else
    {
      if (DEBUG) log("taskViewToUpdate is null, therefore the run command does nothing.");
    }
  }
  public void redo()
  {
    if (DEBUG) log("Attempting to redo this TaskRemoval.");
    run();
  }
}
