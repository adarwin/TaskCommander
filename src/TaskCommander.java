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

public class TaskCommander 
{
  private static Stack<Command> runCommands;
  private static Stack<Command> undonCommands;
  private static TaskEntryPanel taskEntryPanel;
  private static TaskPlanningPanel planningBoard;
  private static JPanel mainContentPanel;
  private static JFrame frame;
  private static JTextField input;
  private static JToolBar toolBar;
  private static ArrayList<Course> courses;
  private static ArrayList<Task> tasks;
  private static boolean DEBUG = true;
  private static String CLASS = "TaskCommander";
  public static int startingWidth = 800;
  public static int startingHeight = 600;
  private static final String TASK_ENTRY = "Task Entry Mode";
  private static final String PLANNING = "Planning Board";

  //Public Methods
    private static void log(String message)
    {
      log(CLASS, message);
    }
    public static void log(String header, String message)
    {
      System.out.println(header + ": " + message);
    }
    public static JFrame getFrame() { return frame; }
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

    public void setSelected(Course course)
    {
      for (Course c : courses)
      {
        if (c == course)
        {
          c.setSelected(true);
          break;
        }
      }
      if (!course.isSelected())
      {
        //Throw exception here
        System.out.println("ERROR");
      }
    }

    public static ArrayList<Task> getTasks() { return tasks; }
    public static ArrayList<Course> getCourses() { return courses; }



  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        createAndShowGUI();
      }
    });
  }






  private static void createAndShowGUI()
  {
    courses = new ArrayList<Course>();
    tasks = new ArrayList<Task>();

    JFrame frame = new JFrame("TaskCommander");
    frame.setPreferredSize(new Dimension(startingWidth, startingHeight));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    addComponentsToFrame(frame);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  private static void addComponentsToFrame(JFrame mainFrame)
  {
    Container contentPane = mainFrame.getContentPane();
    JPanel constantsPanel = new JPanel();
    mainContentPanel = new JPanel(new CardLayout());
    constantsPanel.setLayout(new BorderLayout());
    toolBar = new JToolBar();

    JMenuBar menuBar = new JMenuBar();
    //Build the file menu
      JMenu fileMenu = new JMenu("File");
      fileMenu.setMnemonic(KeyEvent.VK_F);
      JMenuItem menuItem = new JMenuItem("New...", KeyEvent.VK_N);
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
      ActionListener[] listeners = rbMenuItem.getActionListeners();
      JButton temp = new JButton(rbMenuItem.getText());
      for (ActionListener listener : listeners)
      {
        temp.addActionListener(listener);
      }
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
      listeners = rbMenuItem.getActionListeners();
      temp = new JButton(rbMenuItem.getText());
      for (ActionListener listener : listeners)
      {
        temp.addActionListener(listener);
      }
      toolBar.add(temp);

    //Build the tools menu
      JMenu toolsMenu = new JMenu("Tools");
      toolsMenu.setMnemonic(KeyEvent.VK_T);
      JMenu macrosMenu = new JMenu("Macros");
      macrosMenu.setMnemonic(KeyEvent.VK_M);
      menuItem = new JMenuItem("Record");
      macrosMenu.add(menuItem);
      menuItem = new JMenuItem("Manage...");
      macrosMenu.add(menuItem);
      toolsMenu.add(macrosMenu);


    menuBar.add(fileMenu);
    menuBar.add(editMenu);
    menuBar.add(viewMenu);
    menuBar.add(toolsMenu);


    toolBar.setFloatable(false);
    toolBar.setBorderPainted(true);
    //toolBar.setPreferredSize(new Dimension(100, 50));
    toolBar.add(new JButton("ToolbarButton"));


    taskEntryPanel = new TaskEntryPanel();
    planningBoard = new TaskPlanningPanel();
    JButton undo = new JButton("Undo");
    JButton redo = new JButton("Redo");
    input = new JTextField();

    constantsPanel.add(menuBar, BorderLayout.NORTH);
    constantsPanel.add(toolBar, BorderLayout.SOUTH);

    mainContentPanel.add(taskEntryPanel, TASK_ENTRY);
    mainContentPanel.add(planningBoard, PLANNING);

    contentPane.add(constantsPanel, BorderLayout.NORTH);
    //contentPane.add(taskEntryPanel, BorderLayout.CENTER);
    contentPane.add(mainContentPanel, BorderLayout.CENTER);
  }
}
