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
    previousState = previous;
    currentState = previous;
    newState = edited;
  }

  public void run()
  {
    if (DEBUG) log("Now running TaskEdit Command");
    if (taskViewsToUpdate != null)
    {
      if (DEBUG) log("Update currentState to newState");
      currentState.updateFrom(newState);
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
      if (DEBUG) log("Revert task to previous state");
      currentState.updateFrom(previousState);
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
}
