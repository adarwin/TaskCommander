/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.Container;

public class TaskAddition implements Command
{
  TaskWidget taskWidget;
  Container parentComponent;
  public TaskAddition(Container parent, TaskWidget taskWidget)
  {
    this.taskWidget = taskWidget;
    parentComponent = parent;
  }
  public void run()
  {
    if (parentComponent != null)
    {
      parentComponent.add(taskWidget);
    }
  }
  public void undo()
  {
    parentComponent.remove(taskWidget);
  }
  public void redo()
  {
  }
}
