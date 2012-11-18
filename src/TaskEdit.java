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
  //Previous Data
  Task previousState;
  //New Data
  Task newState;

  private void log(String message)
  {
    TaskCommander.log(CLASS, message);
  }
  public TaskEdit(Task previous, Task edited)
  {
    previousState = previous;
    newState = edited;
  }

  public void run()
  {
  }
  public void undo()
  {
  }
  public void redo()
  {
  }
}
