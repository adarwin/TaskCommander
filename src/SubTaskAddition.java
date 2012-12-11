/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.util.ArrayList;

public class SubTaskAddition implements Command
{
  private TaskView taskViewToUpdate;
  private SubTask subTask;
  private boolean DEBUG = true;
  private String CLASS = "SubTaskAddition";

  private void log(String message)
  {
    TaskCommander.log(CLASS, message);
  }
  public SubTaskAddition(SubTask subTask, TaskView taskViewToUpdate)
  {
    if (DEBUG) log("Initializing new SubTaskAddition with name: '" + subTask.getName() + "' and taskViewToUpdate: " + (taskViewToUpdate == null ? null : "non-null") + "\r\n");
    this.subTask = subTask;
    this.taskViewToUpdate = taskViewToUpdate;
  }
  public void run()
  {
    if (DEBUG) log("Now running SubTaskAddition command");
    if (taskViewToUpdate != null)
    {
      if (DEBUG) log("Get existing subTasks from TaskCommander");
      ArrayList<SubTask> subTasks = TaskCommander.getSubTasks();
      if (DEBUG) log("Add the subTask contained in this command to the existing subTasks list");
      subTasks.add(subTask);
      if (DEBUG) log("Add the task to the taskViewToUpdate");
      taskViewToUpdate.addSubTask(subTask);
    }
    else
    {
      if (DEBUG) log("taskViewToUpdate is null, therefore the run command does nothing.");
    }
  }
  public void undo()
  {
    if (DEBUG) log("Attempting to undo SubTaskAddition");
    if (taskViewToUpdate != null)
    {
      if (DEBUG) log("Remove task from taskViewToUpdate");
      taskViewToUpdate.removeSubTask(subTask);
    }
    else
    {
      if (DEBUG) log("Since taskViewToUpdate was null, we did nothing.");
    }
  }
  public void redo()
  {
    if (DEBUG) log("Attempting to redo this SubTaskAddition.");
    run();
  }



  public String toString()
  {
    return "Sub-Task Addition: " + subTask.getName();
  }
}
