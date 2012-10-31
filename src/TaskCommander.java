/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.UIManager.*;

public class TaskCommander 
{
  public static int startingWidth = 800;
  public static int startingHeight = 600;

  protected static final Color pastelGreen = new Color(210, 255, 197);
  protected static final Color pastelBlue = new Color(208, 200, 255);
  protected static final Color pastelYellow = new Color(255, 247, 187);
  protected static final Color pastelCyan = new Color(183, 252, 255);
  protected static final Color pastelPurple = new Color(255, 191, 239);
  protected static final Color pastelRed = new Color(255, 199, 198);
  protected static final Color neutralColor = new Color(214, 217, 223);

  private static Stack<Command> runCommands;
  private static Stack<Command> undonCommands;
  private static TaskEntryPanel taskEntryPanel;
  private static TaskPlanningPanel planningBoard;
  private static JPanel mainContentPanel, constantsPanel;
  private static JFrame mainFrame;
  private static JTextField input;
  private static JToolBar toolBar;
  private static ArrayList<Course> courses;
  private static ArrayList<Task> tasks;
  private static ArrayList<SubTask> subTasks;
  private static Course generalCourse;
  private static SpringLayout layout;
  private static JMenuBar menuBar;
  private static Container contentPane;

  private static boolean DEBUG = true;
  private static String CLASS = "TaskCommander";
  private static final String TASK_ENTRY = "Task Entry Mode";
  private static final String PLANNING = "Planning Board";

  private static void log(String message)
  {
    log(CLASS, message);
  }

  //Public Methods
    public static Color getDefaultCourseColor() { return neutralColor; }

    public static void log(String header, String message)
    {
      System.out.println(header + ": " + message);
    }

    public static JFrame getFrame() { return mainFrame; }

    public static void undo()
    {
      if (runCommands != null && !runCommands.isEmpty())
        {
        Command lastCommand = runCommands.pop();
        lastCommand.undo();
        if (undonCommands == null)
        {
          undonCommands = new Stack<Command>();
        }
        undonCommands.push(lastCommand);
      }
    }
  
    public static void redo()
    {
      if(undonCommands != null && !undonCommands.isEmpty())
      {
        Command lastCommand = undonCommands.pop();
        lastCommand.redo();
        runCommands.push(lastCommand);
      }
    }

    public static void addCommand(Command command)
    {
      if (DEBUG) log("Adding new command");
      if (runCommands == null)
      {
        runCommands = new Stack<Command>();
      }
      //command.run();
      runCommands.push(command);
      if (undonCommands != null)
      {
        undonCommands.clear();
      }
    }

    public static Stack<Command> getRunCommands() { return runCommands; }

    public static Stack<Command> getUndonCommands() { return undonCommands; }

    public static void addCourse(TaskView taskView)
    {
      addCourse("General", taskView);
    }

    public static void addCourse(String name, TaskView taskView)
    {
      Course course = new Course(name);
      addCourse(course, taskView);
    }

    public static void addCourse(Course course, TaskView taskView)
    {
      Command command = new CourseAddition(course, taskView);
      command.run();
      addCommand(command);
    }

    public static void addTask()
    {
      addTask(input.getText());
    }
    
    public static void addTask(String name)
    {
      if (DEBUG) log("Adding task with name: '" + name + "'");
      if (DEBUG) log("Get selected course");
      Course course = getSelectedCourse();
      if (course == null)
      {
        course = generalCourse;
      }
      if (DEBUG) log("Create Task object and add it");
      addTask(new Task(name, course));
    }

    public static void addTask(Task task)
    {
      if (DEBUG) log("Attempting to add Task object");
      if (DEBUG) log("Create the TaskAddition command object");
      Command command = new TaskAddition(task, taskEntryPanel);
      if (DEBUG) log("Run the command");
      command.run();
      if (DEBUG) log("Add the command to the list of commands");
      addCommand(command);

      if (DEBUG) log("Revalidate and repaint taskEntryPanel");
      taskEntryPanel.revalidate();
      taskEntryPanel.repaint();
    }

    public static SubTask addSubTask(String name)
    {
      if (DEBUG) log("Adding subtask with name: '" + name + "'");
      if (DEBUG) log("Get selected task");
      Task task = getSelectedTask();
      SubTask output;
      if (task == null)
      {
        output = null;
      }
      else
      {
        output = addSubTask(new SubTask(name, task));
      }
      return output;
    }

    public static SubTask addSubTask(SubTask subTask)
    {
      if (DEBUG) log("Attempting to add SubTask object");
      if (DEBUG) log("Create the SubTaskAddition command object");
      Command command = new SubTaskAddition(subTask, taskEntryPanel);
      if (DEBUG) log("Run the command");
      command.run();
      if (DEBUG) log("Add the command to the list of commands");
      addCommand(command);
      if (DEBUG) log("Revalidate and repaint taskEntryPanel");
      taskEntryPanel.revalidate();
      taskEntryPanel.repaint();
      return subTask;
    }

    public static void addCourse(String name)
    {
      if (DEBUG) log("Adding course with name: '" + name + "'");
      if (DEBUG) log("Create Course object and add it");
      addCourse(new Course(name));
    }

    public static void addCourse(Course course)
    {
      if (DEBUG) log("Attempting to add Course object");
      if (DEBUG) log("Create the CourseAddition command object");
      Command command = new CourseAddition(course, taskEntryPanel);
      if (DEBUG) log("Run the command");
      command.run();
      if (DEBUG) log("Add the command to the list of commands");
      addCommand(command);

      if (DEBUG) log("Revalidate and repaint taskEntryPanel");
      taskEntryPanel.revalidate();
      taskEntryPanel.repaint();
    }

    public static Course getSelectedCourse()
    {
      if (DEBUG) log("Attempting to determine selected course");
      Course selectedCourse = null;
      if (courses != null)
      {
        for(Course c : courses)
        {
          if (c.isSelected())
          {
            selectedCourse = c;
            break;
          }
        }
      }
      if (DEBUG)
      {
        if (selectedCourse != null)
        {
          log("Found selected course to be: " + selectedCourse.getName());
        }
        else
        {
          log("There was no selected course.");
        }
      }
      return selectedCourse;
    }

    public static Task getSelectedTask()
    {
      if (DEBUG) log("Attempting to determine selected task");
      Task selectedTask = null;
      if (tasks != null)
      {
        for(Task t : tasks)
        {
          if (t.isSelected())
          {
            selectedTask = t;
            break;
          }
        }
      }
      if (DEBUG)
      {
        if (selectedTask != null)
        {
          log("Found selected task to be: " + selectedTask.getName());
        }
        else
        {
          log("There was no selected task.");
        }
      }
      return selectedTask;
    }

    public void setSelected(Course course, boolean selection)
    {
      for (Course c : courses)
      {
        if (c == course)
        {
          c.setSelected(selection);
          break;
        }
      }
      if (!course.isSelected())
      {
        //Throw exception here
        System.out.println("ERROR");
      }
    }

    public static ArrayList<Course> getCourses() { return courses; }

    public static ArrayList<Task> getTasks() { return tasks; }

    public static ArrayList<SubTask> getSubTasks() { return subTasks; }



  public static void main(String[] args)
  {
    try
    {
      for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
      {
        if ("Nimbus".equals(info.getName()))
        {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    }
    catch (UnsupportedLookAndFeelException e)
    {
      e.printStackTrace();
      System.exit(1);
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      System.exit(1);
    }
    catch (InstantiationException e)
    {
      e.printStackTrace();
      System.exit(1);
    }
    catch (IllegalAccessException e)
    {
      e.printStackTrace();
      System.exit(1);
    }

    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        createAndShowGUI();
      }
    });
  }






  // Private methods
    private static void createAndShowGUI()
    {
      courses = new ArrayList<Course>();
      tasks = new ArrayList<Task>();
      subTasks = new ArrayList<SubTask>();

      mainFrame = new JFrame("TaskCommander");
      mainFrame.setPreferredSize(new Dimension(startingWidth, startingHeight));
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      configureComponents();
      buildMenuBar();
      configureLayouts();
      addComponentsToFrame();

      mainFrame.pack();
      mainFrame.setLocationRelativeTo(null);
      mainFrame.setVisible(true);
    }

    private static void buildMenuBar()
    {
      menuBar = new JMenuBar();
      //Build the file menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        JMenuItem menuItem = new JMenuItem("New...", KeyEvent.VK_N);
        menuItem.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            JFrame temp = new JFrame("New... Window");
            temp.setSize(new Dimension(480, 320));
            temp.setLocationRelativeTo(null);
            temp.setVisible(true);
          }
        });
        fileMenu.add(menuItem);
        menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        menuItem.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            System.exit(0);
          }
        });
        fileMenu.addSeparator();
        fileMenu.add(menuItem);

      //Build the edit menu
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        menuItem = new JMenuItem("Undo", KeyEvent.VK_U);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.META_DOWN_MASK));
        menuItem.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            undo();
            taskEntryPanel.revalidate();
            taskEntryPanel.repaint();
          }
        });
        editMenu.add(menuItem);
        menuItem = new JMenuItem("Redo", KeyEvent.VK_R);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.META_DOWN_MASK));
        menuItem.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            redo();
            taskEntryPanel.revalidate();
            taskEntryPanel.repaint();
          }
        });
        editMenu.add(menuItem);

      //Build the view menu
        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic(KeyEvent.VK_V);

        ButtonGroup viewModes = new ButtonGroup();
        JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("Task Entry Mode");
        rbMenuItem.setSelected(true);
        rbMenuItem.setMnemonic(KeyEvent.VK_T);
        rbMenuItem.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            CardLayout c = (CardLayout)(mainContentPanel.getLayout());
            c.show(mainContentPanel, TASK_ENTRY);
          }
        });
        viewModes.add(rbMenuItem);
        viewMenu.add(rbMenuItem);

        //Add to toolbar
          JButton temp = new JButton(rbMenuItem.getText());
          temp.setModel(rbMenuItem.getModel());
          toolBar.add(temp);

        rbMenuItem = new JRadioButtonMenuItem("Planning Board");
        rbMenuItem.setMnemonic(KeyEvent.VK_P);
        rbMenuItem.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            CardLayout c = (CardLayout)(mainContentPanel.getLayout());
            c.show(mainContentPanel, PLANNING);
          }
        });
        viewModes.add(rbMenuItem);
        viewMenu.add(rbMenuItem);

        //Add to toolbar
          temp = new JButton(rbMenuItem.getText());
          temp.setModel(rbMenuItem.getModel());
          toolBar.add(temp);

      //Build the tools menu
        JMenu toolsMenu = new JMenu("Tools");
        toolsMenu.setMnemonic(KeyEvent.VK_T);
        JMenu macrosMenu = new JMenu("Macros");
        macrosMenu.setMnemonic(KeyEvent.VK_M);
        menuItem = new JMenuItem("Record");
        menuItem.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            JFrame temp = new JFrame("Record... Window");
            temp.setSize(new Dimension(480, 320));
            temp.setLocationRelativeTo(null);
            temp.setVisible(true);
          }
        });
        macrosMenu.add(menuItem);
        menuItem = new JMenuItem("Manage...");
        menuItem.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            JFrame temp = new JFrame("Manage... Window");
            temp.setSize(new Dimension(480, 320));
            temp.setLocationRelativeTo(null);
            temp.setVisible(true);
          }
        });
        macrosMenu.add(menuItem);
        toolsMenu.add(macrosMenu);

      // Add menus to menuBar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(toolsMenu);
    }

    private static void configureComponents()
    {
      contentPane = mainFrame.getContentPane();
      constantsPanel = new JPanel();
      mainContentPanel = new JPanel(new CardLayout());
      toolBar = new JToolBar();
      toolBar.setFloatable(true);
      toolBar.setBorderPainted(true);
      toolBar.setBorder(BorderFactory.createLineBorder(Color.black));
      toolBar.add(new JButton("ToolbarButton"));

      taskEntryPanel = new TaskEntryPanel();
      planningBoard = new TaskPlanningPanel();
      input = new JTextField();

    }

    private static void configureLayouts()
    {
      layout = new SpringLayout();
      constantsPanel.setLayout(new BorderLayout());
      contentPane.setLayout(layout);
      layout.putConstraint(SpringLayout.NORTH, constantsPanel, 0, SpringLayout.NORTH, contentPane);
      layout.putConstraint(SpringLayout.EAST, constantsPanel, 0, SpringLayout.EAST, contentPane);
      layout.putConstraint(SpringLayout.WEST, constantsPanel, 0, SpringLayout.WEST, contentPane);
      layout.putConstraint(SpringLayout.NORTH, mainContentPanel, 10, SpringLayout.SOUTH, constantsPanel);
      layout.putConstraint(SpringLayout.SOUTH, mainContentPanel, 0, SpringLayout.SOUTH, contentPane);
      layout.putConstraint(SpringLayout.EAST, mainContentPanel, 0, SpringLayout.EAST, contentPane);
      layout.putConstraint(SpringLayout.WEST, mainContentPanel, 0, SpringLayout.WEST, contentPane);
    }

    private static void addComponentsToFrame()
    {
      constantsPanel.add(menuBar, BorderLayout.NORTH);
      constantsPanel.add(toolBar, BorderLayout.SOUTH);

      mainContentPanel.add(taskEntryPanel, TASK_ENTRY);
      mainContentPanel.add(planningBoard, PLANNING);

      contentPane.add(constantsPanel, BorderLayout.NORTH);
      contentPane.add(mainContentPanel, BorderLayout.CENTER);

      // Add general course
        generalCourse = new Course("General");
        Command command = new CourseAddition(generalCourse, taskEntryPanel);
        command.run();
    }
}
