/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.util.ArrayList;

public class TaskEdit implements Command
{
  private ArrayList<TaskView> taskViewsToUpdate;
  private boolean DEBUG = true;
  private String CLASS = "TaskEdit";
  Task previousState;
  Task currentState;
  Task newState;

  private void log(String message)
  {
    TaskCommander.log(CLASS, message);
  }
  public TaskEdit(Task previous, Task edited)
  {
    taskViewsToUpdate = TaskCommander.getRegisteredTaskViews();
    if (DEBUG) log("Initializing new TaskEdit with task name: '" + previous.getName() + "'");
    previousState = new Task("", null);
    previousState.updateFrom(previous);
    currentState = previous;
    newState = edited;
    if (DEBUG)
    {
      log("previousState completed? " + previousState.isCompleted());
      log("currentState completed? " + currentState.isCompleted());
      log("newState completed? " + newState.isCompleted());
    }
  }

  public void run()
  {
    if (DEBUG) log("Now running TaskEdit Command");
    if (taskViewsToUpdate != null)
    {
      if (DEBUG) log("currentState completed? " + currentState.isCompleted());
      if (DEBUG) log("newState completed? " + newState.isCompleted());
      if (DEBUG) log("Update currentState to newState");
      currentState.updateFrom(newState);
      if (DEBUG) log("currentState completed after update? " + currentState.isCompleted());
      if (DEBUG) log("Update taskViews");
      for (TaskView taskView : taskViewsToUpdate)
      {
        taskView.updateTaskInfo();
      }
    }
  }
  public void undo()
  {
    if (DEBUG) log("Attempting to undo TaskEdit");
    if (taskViewsToUpdate != null)
    {
      if (DEBUG) log("currentState Completed? " + currentState.isCompleted());
      if (DEBUG) log("previousState Completed? " + previousState.isCompleted());
      if (DEBUG) log("Revert task to previous state");
      currentState.updateFrom(previousState);
      if (DEBUG) log("currentState completed after revert? " + currentState.isCompleted());
      if (DEBUG) log("Update taskViews");
      for (TaskView taskView : taskViewsToUpdate)
      {
        taskView.updateTaskInfo();
      }
    }
  }
  public void redo()
  {
    if (DEBUG) log("Attempting to redo this TaskEdit");
    run();
  }



  public String toString()
  {
    if (previousState.getName().equals(newState.getName()))
    {
      return "Task Edit: " + newState.getName();
    }
    else
    {
      return "Task Edit: " + previousState.getName() + " --> " + newState.getName();
    }
  }
}
