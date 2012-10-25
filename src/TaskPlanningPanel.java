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
  }
  public void removeCourse(Course course)
  {
  }
  public void addTask(Task task)
  {
  }
  public void removeTask(Task task)
  {
  }
}
