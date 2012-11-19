/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

public class TaskPlanningPanel extends JPanel implements TaskView
{
  private JSplitPane mainSplitPane;
  private JPanel unPlannedPane, unPlannedContent, plannedContent;
  private JScrollPane unPlannedScrollPane;
  private PlanningCalendar calendar;
  private ArrayList<TaskWidget> taskWidgets;
  private final boolean DEBUG = true;
  private final String CLASS = "TaskPlanningPanel";



  private void log(String message)
  {
    TaskCommander.log(CLASS, message);
  }



  public TaskPlanningPanel()
  {
    // Initialize Components and Containers
      unPlannedPane = new JPanel();
      unPlannedPane.setLayout(new BorderLayout());
      unPlannedContent = new JPanel();
      plannedContent = new JPanel();
      unPlannedScrollPane = new JScrollPane(unPlannedContent);
      calendar = new PlanningCalendar();
      mainSplitPane = new JSplitPane();
      unPlannedPane.add(unPlannedScrollPane, BorderLayout.CENTER);
      taskWidgets = new ArrayList<TaskWidget>();

    // Configure Containers
      plannedContent.setLayout(new BorderLayout());
      mainSplitPane.setLeftComponent(unPlannedPane);
      mainSplitPane.setRightComponent(plannedContent);
      mainSplitPane.setContinuousLayout(true);
      mainSplitPane.setDividerLocation(TaskCommander.startingWidth/3);

    // Set Layouts
      unPlannedContent.setLayout(new BoxLayout(unPlannedContent, BoxLayout.PAGE_AXIS));
      setLayout(new BorderLayout());

    // Add components
      plannedContent.add(calendar, BorderLayout.CENTER);
      add(mainSplitPane, BorderLayout.CENTER);
  }

  public void addCourse(Course course)
  {
    if (DEBUG) log("addCourse(Course course) currently does nothing");
  }
  public void removeCourse(Course course)
  {
    if (DEBUG) log("removeCourse(Course course) currently does nothing");
  }
  public void addTask(Task task)
  {
    if (DEBUG) log("Attempting to add task");
    TaskWidget taskWidget = new TaskWidget(task);
    //taskWidgets.add(taskWidget);
    unPlannedContent.add(taskWidget);
    taskWidgets.add(taskWidget);
  }
  public void removeTask(Task task)
  {
    if (DEBUG) log("Find the taskWidget that contains the desired task");
    TaskWidget target = null;
    for (TaskWidget tw : taskWidgets)
    {
      if (tw.getTask() == task)
      {
        target = tw;
      }
    }
    if (target == null)
    {
      if (DEBUG) log("Failed to find desired taskWidget");
    }
    else
    {
      if (DEBUG) log("Found the desired taskWidget...now just remove it");
      taskWidgets.remove(target);
      unPlannedContent.remove(target);
      repaint();
    }
  }
  public void addSubTask(SubTask subTask)
  {
    if (DEBUG) log("addSubTask(SubTask subTask) currently does nothing");
  }
  public void removeSubTask(SubTask subTask)
  {
    if (DEBUG) log("removeSubTask(SubTask subTask) currently does nothing");
  }
  public void updateTaskColors()
  {
    if (taskWidgets != null)
    {
      for (TaskWidget taskWidget : taskWidgets)
      {
        taskWidget.updateColor();
      }
    }
  }
  public void updateTaskInfo()
  {
    System.out.println("updateTaskInfo() currently does nothing");
  }



  private void populateUnplannedContent()
  {
    ArrayList<Task> tasks = TaskCommander.getTasks();
    for (Task task : tasks)
    {
      addTask(task);
    }
  }
}
