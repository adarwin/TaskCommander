/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;

public class TaskPlanningPanel extends JPanel implements TaskView
{
  JSplitPane mainSplitPane;
  JPanel unPlannedContent, plannedContent;
  PlanningCalendar calendar;

  public TaskPlanningPanel()
  {
    super();
    // Initialize Components and Containers
      // Panels
        unPlannedContent = new JPanel();
        plannedContent = new JPanel();
      calendar = new PlanningCalendar();
      mainSplitPane = new JSplitPane();

    // Configure Containers
      mainSplitPane.setLeftComponent(unPlannedContent);
      mainSplitPane.setRightComponent(plannedContent);
      mainSplitPane.setContinuousLayout(true);
      mainSplitPane.setDividerLocation(TaskCommander.startingWidth/3);
      setLayout(new BorderLayout());

    // Add components
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
