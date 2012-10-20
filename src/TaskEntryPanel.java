/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.Arrays;
import java.util.List;
import javax.swing.border.TitledBorder;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class TaskEntryPanel extends JPanel
{
  JSplitPane mainSplitPane;
  JSplitPane nestedSplitPane;
  JPanel leftContent, centerContent, rightContent;
  JPanel leftPane, centerPane, rightPane;
  SpringLayout layout, centerLayout;
  JTextField quickTaskField;
  JButton taskAddButton;
  JScrollPane leftScrollPane, centerScrollPane, rightScrollPane;

  public TaskEntryPanel()
  {
    super();
    // Initialize Components and Containers
      // Split Panes
        mainSplitPane = new JSplitPane();
        nestedSplitPane = new JSplitPane();

      // Panels
        leftContent = new JPanel();
        centerContent = new JPanel();
        rightContent = new JPanel();
        leftPane = new JPanel();
        centerPane = new JPanel();
        rightPane = new JPanel();

      // Scroll Panes
        leftScrollPane = new JScrollPane(leftContent);
        centerScrollPane = new JScrollPane(centerContent);
        rightScrollPane = new JScrollPane(rightContent);

      // Other Components
        quickTaskField = new JTextField();
        taskAddButton = new JButton("+");

      // Layouts
        centerLayout = new SpringLayout();
        layout = new SpringLayout();


    //Configure Components
      taskAddButton.setPreferredSize(new Dimension(20, 20));
      quickTaskField.setText("Enter new task here");
      centerScrollPane.setMinimumSize(new Dimension(300, 0));


    //Configure Containers
      mainSplitPane.setRightComponent(nestedSplitPane);
      mainSplitPane.setLeftComponent(leftScrollPane);
      nestedSplitPane.setLeftComponent(centerPane);
      nestedSplitPane.setRightComponent(rightScrollPane);

      mainSplitPane.setContinuousLayout(true);
      nestedSplitPane.setContinuousLayout(true);


    //Add components
      centerPane.add(quickTaskField);
      centerPane.add(taskAddButton);
      centerPane.add(centerScrollPane);
      add(mainSplitPane);


    //Set Layouts
      setLayout(layout);
      leftContent.setLayout(new BoxLayout(leftContent, BoxLayout.PAGE_AXIS));
      centerContent.setLayout(new BoxLayout(centerContent, BoxLayout.PAGE_AXIS));
      rightContent.setLayout(new BoxLayout(rightContent, BoxLayout.PAGE_AXIS));
      centerPane.setLayout(centerLayout);


    //Configure Layouts
      centerLayout.putConstraint(SpringLayout.NORTH, taskAddButton, 0, SpringLayout.NORTH, quickTaskField);
      centerLayout.putConstraint(SpringLayout.SOUTH, taskAddButton, 0, SpringLayout.SOUTH, quickTaskField);
      centerLayout.putConstraint(SpringLayout.EAST, taskAddButton, 0, SpringLayout.EAST, centerPane);
    
      centerLayout.putConstraint(SpringLayout.NORTH, quickTaskField, 0, SpringLayout.NORTH, centerPane);
      centerLayout.putConstraint(SpringLayout.EAST, quickTaskField, 0, SpringLayout.WEST, taskAddButton);
      centerLayout.putConstraint(SpringLayout.WEST, quickTaskField, 0, SpringLayout.WEST, centerPane);


      centerLayout.putConstraint(SpringLayout.NORTH, centerScrollPane, 0, SpringLayout.SOUTH,
                                 quickTaskField.getPreferredSize().height > taskAddButton.getPreferredSize().height ? quickTaskField : taskAddButton);
      centerLayout.putConstraint(SpringLayout.SOUTH, centerScrollPane, 0, SpringLayout.SOUTH, centerPane);
      centerLayout.putConstraint(SpringLayout.EAST, centerScrollPane, 0, SpringLayout.EAST, centerPane);
      centerLayout.putConstraint(SpringLayout.WEST, centerScrollPane, 0, SpringLayout.WEST, centerPane);


      layout.putConstraint(SpringLayout.NORTH, mainSplitPane, 0, SpringLayout.NORTH, this);
      layout.putConstraint(SpringLayout.SOUTH, mainSplitPane, 0, SpringLayout.SOUTH, this);
      layout.putConstraint(SpringLayout.EAST, mainSplitPane, 0, SpringLayout.EAST, this);
      layout.putConstraint(SpringLayout.WEST, mainSplitPane, 0, SpringLayout.WEST, this);



    //Add Action Listeners
      quickTaskField.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          TaskCommander.addTask(quickTaskField.getText());
        }
      });
      taskAddButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          TaskCommander.addTask(quickTaskField.getText());
        }
      });
  }


  public String getQuickTaskFieldText()
  {
    return quickTaskField.getText();
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
