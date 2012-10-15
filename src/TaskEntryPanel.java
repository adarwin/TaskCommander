/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;
import javax.swing.border.TitledBorder;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SpringLayout;

public class TaskEntryPanel extends JPanel
{
  JSplitPane mainSplitPane;
  JSplitPane nestedSplitPane;
  JPanel leftContent, centerContent, rightContent;
  SpringLayout layout;

  public TaskEntryPanel()
  {
    super();
    mainSplitPane = new JSplitPane();
    nestedSplitPane = new JSplitPane();
    leftContent = new JPanel();
    centerContent = new JPanel();
    rightContent = new JPanel();

    leftContent.setLayout(new BoxLayout(leftContent, BoxLayout.PAGE_AXIS));
    centerContent.setLayout(new BoxLayout(centerContent, BoxLayout.PAGE_AXIS));
    rightContent.setLayout(new BoxLayout(rightContent, BoxLayout.PAGE_AXIS));
    JScrollPane centerScrollPane = new JScrollPane(centerContent);
    centerScrollPane.setMinimumSize(new Dimension(300, 0));

    mainSplitPane.setRightComponent(nestedSplitPane);
    mainSplitPane.setLeftComponent(new JScrollPane(leftContent));
    nestedSplitPane.setLeftComponent(centerScrollPane);
    nestedSplitPane.setRightComponent(new JScrollPane(rightContent));


    mainSplitPane.setContinuousLayout(true);
    nestedSplitPane.setContinuousLayout(true);

    add(mainSplitPane);
    layout = new SpringLayout();
    layout.putConstraint(SpringLayout.NORTH, mainSplitPane, 0, SpringLayout.NORTH, this);
    layout.putConstraint(SpringLayout.SOUTH, mainSplitPane, 0, SpringLayout.SOUTH, this);
    layout.putConstraint(SpringLayout.EAST, mainSplitPane, 0, SpringLayout.EAST, this);
    layout.putConstraint(SpringLayout.WEST, mainSplitPane, 0, SpringLayout.WEST, this);
    setLayout(layout);
  }


  public void addCourseWidget(CourseWidget courseWidget)
  {
    if (courseWidget == null)
    {
      courseWidget = new CourseWidget();
    }
    leftContent.add(courseWidget);
  }


  public void addTaskWidget()
  {
    addTaskWidget(new TaskWidget());
  }
  public void addTaskWidget(TaskWidget taskWidget)
  {
    if (taskWidget == null)
    {
      taskWidget = new TaskWidget();
    }
    System.out.println("Adding taskwidget");
    centerContent.add(taskWidget);
  }
  public void removeTaskWidget(TaskWidget taskWidget)
  {
    if (taskWidget != null)
    {
      centerContent.remove(taskWidget);
    }
  }

  public void addTaskWidgetDivider(String text)
  {
    centerContent.add(new TaskWidgetDivider(text));
    centerContent.repaint();
  }
}
