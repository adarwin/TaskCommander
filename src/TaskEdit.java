/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

public class TaskEdit implements Command
{
  //Previous Data
  Task previousState;
  //New Data
  Task newState;

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
