/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
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
import javax.swing.BorderFactory;

public class TaskEntryPanel extends JPanel implements TaskView
{
  JSplitPane mainSplitPane, nestedSplitPane;
  JPanel leftContent, centerContent, rightContent;
  JPanel leftPane, centerPane, rightPane;
  JScrollPane leftScrollPane, centerScrollPane, rightScrollPane;
  JTextField quickTaskField, quickCourseField, quickSubTaskField;
  JButton taskAddButton, courseAddButton, subTaskAddButton;
  SpringLayout layout, centerLayout, leftLayout, rightLayout;
  private ArrayList<CourseWidget> courseWidgets;
  private ArrayList<TaskWidget> taskWidgets;
  private ArrayList<SubTaskWidget> subTaskWidgets;
  private CourseWidget previouslySelectedCourseWidget;
  private String quickTaskText = "Enter new task name here";
  private String quickSubTaskText = "Enter new subtask here";
  private String quickCourseText = "Enter new course name here";
  private final boolean DEBUG = true;
  private final String CLASS = "TaskEntryPanel";

  private void log(String message)
  {
    TaskCommander.log(CLASS, message);
  }

  public void updateTaskColors()
  {
    for (TaskWidget taskWidget : taskWidgets)
    {
      taskWidget.updateColor();
    }
  }
  protected void setSelectedCourseWidget(CourseWidget courseWidget, boolean selected)
  {
    if (previouslySelectedCourseWidget != null)
    {
      previouslySelectedCourseWidget.setSelected(!selected);
    }
    courseWidget.setSelected(selected);
    previouslySelectedCourseWidget = courseWidget;
  }

  public TaskEntryPanel()
  {
    courseWidgets = new ArrayList<CourseWidget>();
    taskWidgets = new ArrayList<TaskWidget>();
    subTaskWidgets = new ArrayList<SubTaskWidget>();
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
        quickCourseField = new JTextField();
        quickTaskField = new JTextField();
        quickSubTaskField = new JTextField();
        courseAddButton = new JButton("+");
        taskAddButton = new JButton("+");
        subTaskAddButton = new JButton("+");

      // Layouts
        centerLayout = new SpringLayout();
        leftLayout = new SpringLayout();
        rightLayout = new SpringLayout();
        layout = new SpringLayout();


    //Configure Components
      courseAddButton.setPreferredSize(new Dimension(40, 20));
      taskAddButton.setPreferredSize(new Dimension(40, 20));
      subTaskAddButton.setPreferredSize(new Dimension(40, 20));
      quickCourseField.setText(quickCourseText);
      quickCourseField.setForeground(Color.gray);
      quickTaskField.setText(quickTaskText);
      quickTaskField.setForeground(Color.gray);
      quickSubTaskField.setText(quickSubTaskText);
      quickSubTaskField.setForeground(Color.gray);
      centerScrollPane.setMinimumSize(new Dimension(300, 0));


    //Configure Containers
      /*
      leftPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                                                          "Courses",
                                                          TitledBorder.LEFT,
                                                          TitledBorder.TOP));
      */
      leftPane.setMinimumSize(new Dimension(220, 0));
      centerPane.setMinimumSize(new Dimension(300, 0));
      rightPane.setMinimumSize(new Dimension(220, 0));
      quickSubTaskField.setEnabled(false);
      subTaskAddButton.setEnabled(false);
      mainSplitPane.setRightComponent(nestedSplitPane);
      mainSplitPane.setLeftComponent(leftPane);
      nestedSplitPane.setLeftComponent(centerPane);
      nestedSplitPane.setRightComponent(rightPane);

      mainSplitPane.setContinuousLayout(true);
      nestedSplitPane.setContinuousLayout(true);

      //mainSplitPane.setDividerLocation(TaskCommander.startingWidth/3);
      //nestedSplitPane.setDividerLocation(TaskCommander.startingWidth/3);
      mainSplitPane.setResizeWeight(.2);
      nestedSplitPane.setResizeWeight(1);



    //Add components
      centerPane.add(quickTaskField);
      centerPane.add(taskAddButton);
      centerPane.add(centerScrollPane);

      leftPane.add(quickCourseField);
      leftPane.add(courseAddButton);
      leftPane.add(leftScrollPane);

      rightPane.add(quickSubTaskField);
      rightPane.add(subTaskAddButton);
      rightPane.add(rightScrollPane);

      add(mainSplitPane);


    //Set Layouts
      setLayout(layout);
      leftContent.setLayout(new BoxLayout(leftContent, BoxLayout.PAGE_AXIS));
      centerContent.setLayout(new BoxLayout(centerContent, BoxLayout.PAGE_AXIS));
      rightContent.setLayout(new BoxLayout(rightContent, BoxLayout.PAGE_AXIS));
      centerPane.setLayout(centerLayout);
      leftPane.setLayout(leftLayout);
      rightPane.setLayout(rightLayout);


    //Configure Layouts
      int margin = 3;
      centerLayout.putConstraint(SpringLayout.NORTH, taskAddButton, 0, SpringLayout.NORTH, quickTaskField);
      centerLayout.putConstraint(SpringLayout.SOUTH, taskAddButton, 0, SpringLayout.SOUTH, quickTaskField);
      centerLayout.putConstraint(SpringLayout.EAST, taskAddButton, 0, SpringLayout.EAST, centerPane);
    
      centerLayout.putConstraint(SpringLayout.NORTH, quickTaskField, 0, SpringLayout.NORTH, centerPane);
      centerLayout.putConstraint(SpringLayout.EAST, quickTaskField, 0, SpringLayout.WEST, taskAddButton);
      centerLayout.putConstraint(SpringLayout.WEST, quickTaskField, 0, SpringLayout.WEST, centerPane);


      centerLayout.putConstraint(SpringLayout.NORTH, centerScrollPane, margin, SpringLayout.SOUTH,
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

      leftLayout.putConstraint(SpringLayout.NORTH, leftScrollPane, margin, SpringLayout.SOUTH,
                                 quickCourseField.getPreferredSize().height > courseAddButton.getPreferredSize().height ? quickCourseField : courseAddButton);
      leftLayout.putConstraint(SpringLayout.SOUTH, leftScrollPane, 0, SpringLayout.SOUTH, leftPane);
      leftLayout.putConstraint(SpringLayout.EAST, leftScrollPane, 0, SpringLayout.EAST, leftPane);
      leftLayout.putConstraint(SpringLayout.WEST, leftScrollPane, 0, SpringLayout.WEST, leftPane);




      rightLayout.putConstraint(SpringLayout.NORTH, subTaskAddButton, 0, SpringLayout.NORTH, quickSubTaskField);
      rightLayout.putConstraint(SpringLayout.SOUTH, subTaskAddButton, 0, SpringLayout.SOUTH, quickSubTaskField);
      rightLayout.putConstraint(SpringLayout.EAST, subTaskAddButton, 0, SpringLayout.EAST, rightPane);

      rightLayout.putConstraint(SpringLayout.NORTH, quickSubTaskField, 0, SpringLayout.NORTH, rightPane);
      rightLayout.putConstraint(SpringLayout.EAST, quickSubTaskField, 0, SpringLayout.WEST, subTaskAddButton);
      rightLayout.putConstraint(SpringLayout.WEST, quickSubTaskField, 0, SpringLayout.WEST, rightPane);

      rightLayout.putConstraint(SpringLayout.NORTH, rightScrollPane, margin, SpringLayout.SOUTH,
                                 quickSubTaskField.getPreferredSize().height > subTaskAddButton.getPreferredSize().height ? quickSubTaskField : subTaskAddButton);
      rightLayout.putConstraint(SpringLayout.SOUTH, rightScrollPane, 0, SpringLayout.SOUTH, rightPane);
      rightLayout.putConstraint(SpringLayout.EAST, rightScrollPane, 0, SpringLayout.EAST, rightPane);
      rightLayout.putConstraint(SpringLayout.WEST, rightScrollPane, 0, SpringLayout.WEST, rightPane);





      layout.putConstraint(SpringLayout.NORTH, mainSplitPane, 0, SpringLayout.NORTH, this);
      layout.putConstraint(SpringLayout.SOUTH, mainSplitPane, 0, SpringLayout.SOUTH, this);
      layout.putConstraint(SpringLayout.EAST, mainSplitPane, 0, SpringLayout.EAST, this);
      layout.putConstraint(SpringLayout.WEST, mainSplitPane, 0, SpringLayout.WEST, this);



    //Add Listeners
      quickCourseField.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          if (DEBUG) log("Intercepted entry from quickCourseField");
          TaskCommander.addCourse(quickCourseField.getText());
          quickCourseField.selectAll();
        }
      });
      quickCourseField.addFocusListener(new FocusListener()
      {
        public void focusGained(FocusEvent e)
        {
          quickCourseField.setText("");
          quickCourseField.setForeground(Color.black);
        }
        public void focusLost(FocusEvent e)
        {
          quickCourseField.setText(quickCourseText);
          quickCourseField.setForeground(Color.gray);
        }
      });
      quickTaskField.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          if (DEBUG) log("Intercepted entry from quickTaskField");
          addTask(quickTaskField.getText());
          quickTaskField.selectAll();
        }
      });
      quickTaskField.addFocusListener(new FocusListener()
      {
        public void focusGained(FocusEvent e)
        {
          quickTaskField.setText("");
          quickTaskField.setForeground(Color.black);
        }
        public void focusLost(FocusEvent e)
        {
          quickTaskField.setText(quickTaskText);
          quickTaskField.setForeground(Color.gray);
        }
      });
      quickSubTaskField.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          if (DEBUG) log("Intercepted entry from quickSubTaskField");
          TaskCommander.addSubTask(quickSubTaskField.getText());
          quickSubTaskField.selectAll();
        }
      });
      quickSubTaskField.addFocusListener(new FocusListener()
      {
        public void focusGained(FocusEvent e)
        {
          quickSubTaskField.setText("");
          quickSubTaskField.setForeground(Color.black);
        }
        public void focusLost(FocusEvent e)
        {
          quickSubTaskField.setText(quickSubTaskText);
          quickSubTaskField.setForeground(Color.gray);
        }
      });
      taskAddButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          if (DEBUG) log("Intercepted click on taskAddButton");
          addTask(quickTaskField.getText());
        }
      });
      subTaskAddButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          if (DEBUG) log("Intercepted click on subTaskAddButton");
          TaskCommander.addSubTask(quickSubTaskField.getText());
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
    revalidate();
    //repaint();
  }
  private void addTask(String name)
  {
    TaskCommander.addTask(name, this);
  }
  public void addSubTask(SubTask subTask)
  {
    if (DEBUG) log("Attempt to add new subTask");
    SubTaskWidget subTaskWidget = new SubTaskWidget(subTask);
    subTaskWidgets.add(subTaskWidget);
    rightContent.add(subTaskWidget);
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
    repaint();
    //revalidate();
  }
  public void removeSubTask(SubTask subTask)
  {
    if (DEBUG) log("Find the subTaskWidget that contains the desired subTask");
    SubTaskWidget target = null;
    for (SubTaskWidget stw : subTaskWidgets)
    {
      if (stw.getSubTask() == subTask)
      {
        target = stw;
      }
    }
    if (target == null)
    {
      if (DEBUG) log("Failed to find desired subTaskWidget");
    }
    else
    {
      if (DEBUG) log("Found the desired subTaskWidget...now just remove it");
      subTaskWidgets.remove(target);
      rightContent.remove(target);
    }
  }
}
