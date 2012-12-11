/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

public class TaskPlanningPanel extends JPanel implements TaskView
{
  private JSplitPane mainSplitPane;
  private JPanel unPlannedPane, unPlannedHeader, unPlannedContent, plannedContent;
  private JButton newCourseButton, newTaskButton;
  private JScrollPane unPlannedScrollPane;
  private PlanningCalendar calendar;
  private ArrayList<TaskWidget> taskWidgets;
  private final boolean DEBUG = true;
  private final String CLASS = "TaskPlanningPanel";



  private void log(String message)
  {
    TaskCommander.log(CLASS, message);
  }



  private void configureComponents()
  {
    taskWidgets = new ArrayList<TaskWidget>();

    unPlannedPane = new JPanel();
    unPlannedHeader = new JPanel();
    newCourseButton = new JButton("Add Course");
    newCourseButton.setToolTipText("Note that the new course will not be visible in Planning Board");
    newTaskButton = new JButton("Add Task");
    newTaskButton.setToolTipText("This will add a new task to the 'General' course.");
    unPlannedContent = new JPanel();
    unPlannedScrollPane = new JScrollPane(unPlannedContent);

    plannedContent = new JPanel();

    calendar = new PlanningCalendar();

    mainSplitPane = new JSplitPane();

    mainSplitPane.setLeftComponent(unPlannedPane);
    mainSplitPane.setRightComponent(plannedContent);
    mainSplitPane.setContinuousLayout(true);
    mainSplitPane.setDividerLocation(TaskCommander.startingWidth/3);
  }


  private void configureLayouts()
  {
    setLayout(new BorderLayout());

    unPlannedPane.setLayout(new BorderLayout());
    unPlannedHeader.setLayout(new BorderLayout());
    unPlannedContent.setLayout(new BoxLayout(unPlannedContent, BoxLayout.PAGE_AXIS));

    plannedContent.setLayout(new BorderLayout());
  }


  private void addComponents()
  {
    unPlannedHeader.add(newCourseButton, BorderLayout.WEST);
    unPlannedHeader.add(newTaskButton, BorderLayout.EAST);
    unPlannedPane.add(unPlannedHeader, BorderLayout.NORTH);
    unPlannedPane.add(unPlannedScrollPane, BorderLayout.CENTER);
    plannedContent.add(calendar, BorderLayout.CENTER);
    add(mainSplitPane, BorderLayout.CENTER);
  }


  private void addListeners()
  {
    newCourseButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        TaskCommander.showNewCourseDialog();
      }
    });
    newTaskButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        TaskCommander.showNewTaskDialog();
      }
    });
  }


  public TaskPlanningPanel()
  {
    configureComponents();
    configureLayouts();
    addComponents();
    addListeners();
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
    if (DEBUG) log("Attempting to update task info for all task widgets");
    for (TaskWidget taskWidget : taskWidgets)
    {
      taskWidget.updateTaskInfo();
    }
  }
  public void updateCourseInfo()
  {
    if (DEBUG) log("updateCourseInfo() currently does nothing");
  }
  public void showTasksFor(Course course)
  {
    if (DEBUG) log("showTasksFor(Course couse) currently does nothing");
  }
  public void showSubTasksFor(Task task)
  {
    if (DEBUG) log("showSubTasksFor(Task task) currently does nothing");
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
