/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

public class TaskPlanningPanel extends JPanel implements TaskView
{
  JSplitPane mainSplitPane;
  JPanel unPlannedContent, plannedContent;
  JScrollPane unPlannedScrollPane;
  PlanningCalendar calendar;
  private final boolean DEBUG = true;
  private final String CLASS = "TaskPlanningPanel";

  private void log(String message)
  {
    TaskCommander.log(CLASS, message);
  }

  public TaskPlanningPanel()
  {
    super();
    // Initialize Components and Containers
      unPlannedContent = new JPanel();
      plannedContent = new JPanel();
      unPlannedScrollPane = new JScrollPane(unPlannedContent);
      calendar = new PlanningCalendar();
      mainSplitPane = new JSplitPane();

    // Configure Containers
      plannedContent.setLayout(new BorderLayout());
      mainSplitPane.setLeftComponent(unPlannedContent);
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
    if (DEBUG) log("addTask(Task task) currently does nothing");
  }
  public void removeTask(Task task)
  {
    if (DEBUG) log("removeTask(Task task) currently does nothing");
  }
  public void addSubTask(SubTask subTask)
  {
    if (DEBUG) log("addSubTask(SubTask subTask) currently does nothing");
  }
  public void removeSubTask(SubTask subTask)
  {
    if (DEBUG) log("removeSubTask(SubTask subTask) currently does nothing");
  }
}
