/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

public class SubTask
{
  private Task associatedTask;
  private String subTaskName;

  public SubTask(String name, Task task)
  {
    associatedTask = task;
    subTaskName = name;
  }

  public String getName() { return subTaskName; }
  public Task getTask() { return associatedTask; }
}
