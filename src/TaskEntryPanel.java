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
import java.util.ArrayList;
import javax.swing.border.TitledBorder;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class TaskEntryPanel extends JPanel implements TaskView
{
  JSplitPane mainSplitPane, nestedSplitPane;
  JPanel leftContent, centerContent, rightContent;
  JPanel leftPane, centerPane, rightPane;
  JScrollPane leftScrollPane, centerScrollPane, rightScrollPane;
  JTextField quickTaskField, quickCourseField;
  JButton taskAddButton, courseAddButton;
  SpringLayout layout, centerLayout, leftLayout;
  private ArrayList<TaskWidget> taskWidgets;
  private ArrayList<CourseWidget> courseWidgets;
  private final boolean DEBUG = true;
  private final String CLASS = "TaskEntryPanel";

  private void log(String message)
  {
    TaskCommander.log(CLASS, message);
  }

  public TaskEntryPanel()
  {
    taskWidgets = new ArrayList<TaskWidget>();
    courseWidgets = new ArrayList<CourseWidget>();
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
        quickCourseField = new JTextField();
        taskAddButton = new JButton("+");
        courseAddButton = new JButton("+");

      // Layouts
        centerLayout = new SpringLayout();
        leftLayout = new SpringLayout();
        layout = new SpringLayout();


    //Configure Components
      taskAddButton.setPreferredSize(new Dimension(40, 20));
      courseAddButton.setPreferredSize(new Dimension(40, 20));
      quickTaskField.setText("Enter new task here");
      quickCourseField.setText("Enter new course name here");
      centerScrollPane.setMinimumSize(new Dimension(300, 0));


    //Configure Containers
      mainSplitPane.setRightComponent(nestedSplitPane);
      mainSplitPane.setLeftComponent(leftPane);
      nestedSplitPane.setLeftComponent(centerPane);
      nestedSplitPane.setRightComponent(rightScrollPane);

      mainSplitPane.setContinuousLayout(true);
      nestedSplitPane.setContinuousLayout(true);

      mainSplitPane.setDividerLocation(TaskCommander.startingWidth/3);
      nestedSplitPane.setDividerLocation(TaskCommander.startingWidth/3);
      mainSplitPane.setResizeWeight(.2);
      nestedSplitPane.setResizeWeight(1);



    //Add components
      centerPane.add(quickTaskField);
      centerPane.add(taskAddButton);
      centerPane.add(centerScrollPane);

      leftPane.add(quickCourseField);
      leftPane.add(courseAddButton);
      leftPane.add(leftScrollPane);

      add(mainSplitPane);


    //Set Layouts
      setLayout(layout);
      leftContent.setLayout(new BoxLayout(leftContent, BoxLayout.PAGE_AXIS));
      centerContent.setLayout(new BoxLayout(centerContent, BoxLayout.PAGE_AXIS));
      rightContent.setLayout(new BoxLayout(rightContent, BoxLayout.PAGE_AXIS));
      centerPane.setLayout(centerLayout);
      leftPane.setLayout(leftLayout);


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



      leftLayout.putConstraint(SpringLayout.NORTH, courseAddButton, 0, SpringLayout.NORTH, quickCourseField);
      leftLayout.putConstraint(SpringLayout.SOUTH, courseAddButton, 0, SpringLayout.SOUTH, quickCourseField);
      leftLayout.putConstraint(SpringLayout.EAST, courseAddButton, 0, SpringLayout.EAST, leftPane);

      leftLayout.putConstraint(SpringLayout.NORTH, quickCourseField, 0, SpringLayout.NORTH, leftPane);
      leftLayout.putConstraint(SpringLayout.EAST, quickCourseField, 0, SpringLayout.WEST, courseAddButton);
      leftLayout.putConstraint(SpringLayout.WEST, quickCourseField, 0, SpringLayout.WEST, leftPane);

      leftLayout.putConstraint(SpringLayout.NORTH, leftScrollPane, 0, SpringLayout.SOUTH,
                                 quickCourseField.getPreferredSize().height > courseAddButton.getPreferredSize().height ? quickCourseField : courseAddButton);
      leftLayout.putConstraint(SpringLayout.SOUTH, leftScrollPane, 0, SpringLayout.SOUTH, leftPane);
      leftLayout.putConstraint(SpringLayout.EAST, leftScrollPane, 0, SpringLayout.EAST, leftPane);
      leftLayout.putConstraint(SpringLayout.WEST, leftScrollPane, 0, SpringLayout.WEST, leftPane);



      layout.putConstraint(SpringLayout.NORTH, mainSplitPane, 0, SpringLayout.NORTH, this);
      layout.putConstraint(SpringLayout.SOUTH, mainSplitPane, 0, SpringLayout.SOUTH, this);
      layout.putConstraint(SpringLayout.EAST, mainSplitPane, 0, SpringLayout.EAST, this);
      layout.putConstraint(SpringLayout.WEST, mainSplitPane, 0, SpringLayout.WEST, this);



    //Add Action Listeners
      quickTaskField.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          if (DEBUG) log("Intercepted entry from quickTaskField");
          TaskCommander.addTask(quickTaskField.getText());
          quickTaskField.selectAll();
        }
      });
      quickTaskField.addFocusListener(new FocusListener()
      {
        public void focusGained(FocusEvent e)
        {
        }
        public void focusLost(FocusEvent e)
        {
        }
      });
      quickCourseField.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          if (DEBUG) log("Intercepted entry from quickCourseField");
          TaskCommander.addCourse(quickCourseField.getText());
          quickCourseField.selectAll();
        }
      });
      taskAddButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          if (DEBUG) log("Intercepted click on taskAddButton");
          TaskCommander.addTask(quickTaskField.getText());
        }
      });
      courseAddButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          if (DEBUG) log("Intercepted click on courseAddButton");
          TaskCommander.addCourse(quickCourseField.getText());
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
    taskWidgets.add(taskWidget);
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




  public void addCourse(Course course)
  {
    if (DEBUG) log("Attempt to add new courseWidget");
    CourseWidget courseWidget = new CourseWidget(course);
    courseWidgets.add(courseWidget);
    leftContent.add(courseWidget);
  }
  public void addTask(Task task)
  {
    if (DEBUG) log("Attempt to add new taskWidget");
    TaskWidget taskWidget = new TaskWidget(task);
    taskWidgets.add(taskWidget);
    centerContent.add(taskWidget);
  }
  public void removeCourse(Course course)
  {
    if (DEBUG) log("Find the courseWidget that contains the desired course");
    CourseWidget target = null;
    for (CourseWidget cw : courseWidgets)
    {
      if (cw.getCourse() == course)
      {
        target = cw;
      }
    }
    if (target == null)
    {
      if (DEBUG) log("Failed to find desired courseWidget");
    }
    else
    {
      if (DEBUG) log("Found the desired courseWidget...now just remove it");
      courseWidgets.remove(target);
      leftContent.remove(target);
    }
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
      centerContent.remove(target);
    }
  }
}
