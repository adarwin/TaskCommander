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
  TaskEntryPanel taskEntryPanel;
  public TaskAddition(TaskEntryPanel taskEntryPanel, TaskWidget taskWidget)
  {
    this.taskWidget = taskWidget;
    this.taskEntryPanel = taskEntryPanel;
  }
  public void run()
  {
    if (taskEntryPanel != null)
    {
      taskEntryPanel.addTaskWidget(taskWidget);
    }
  }
  public void undo()
  {
    if (taskEntryPanel != null)
    {
      System.out.println("Undo");
      taskEntryPanel.removeTaskWidget(taskWidget);
    }
  }
  public void redo()
  {
    run();
  }
}
