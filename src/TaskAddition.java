/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.Container;
import java.util.ArrayList;

public class TaskAddition implements Command
{
  private TaskView[] taskViewsToUpdate;
  private Task task;
  private boolean DEBUG = true;
  private String CLASS = "TaskAddition";

  private void log(String message)
  {
    TaskCommander.log(CLASS, message);
  }
  public TaskAddition(Task task, TaskView[] taskViewsToUpdate)
  {
    if (DEBUG) log("Initializing new TaskAddition with task name: '" + task.getName() + "' and taskViewToUpdate: " + (taskViewsToUpdate == null ? null : "non-null") + "\r\n");
    this.task = task;
    this.taskViewsToUpdate = taskViewsToUpdate;
  }
  public void run()
  {
    if (DEBUG) log("Now running TaskAddition command");
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
      //taskViewToUpdate.addTask(task);
    }
    else
    {
      if (DEBUG) log("taskViewToUpdate is null, therefore the run command does nothing.");
    }
  }
  public void undo()
  {
    if (DEBUG) log("Attempting to undo TaskAddition");
    if (taskViewsToUpdate != null)
    {
      if (DEBUG) log("Remove task from taskViewToUpdate");
      for (TaskView taskView : taskViewsToUpdate)
      {
        taskView.removeTask(task);
      }
      //taskViewToUpdate.removeTask(task);
    }
    else
    {
      if (DEBUG) log("Since taskViewToUpdate was null, we did nothing.");
    }
  }
  public void redo()
  {
    if (DEBUG) log("Attempting to redo this TaskAddition.");
    run();
  }
}
