/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.UIManager.*;
//import com.jtattoo.plaf.texture.TextureLookAndFeel;

public class TaskCommander 
{
  public static int startingWidth = 800;
  public static int startingHeight = 600;

  protected static final Color pastelGreen = new Color(210, 255, 197);
  protected static final Color pastelBlue = new Color(208, 200, 255);
  protected static final Color selectionColor = new Color(255, 250, 212); //Currently the same as pastelblue
  protected static final Color pastelYellow = new Color(255, 247, 187);
  protected static final Color pastelCyan = new Color(183, 252, 255);
  protected static final Color pastelPurple = new Color(255, 191, 239);
  protected static final Color pastelRed = new Color(255, 199, 198);
  protected static final Color neutralColor = new Color(214, 217, 223);

  // Colors from Apple Corporation
    protected static final Color melon = new Color(255, 204, 102);
    protected static final Color salmon = new Color(255, 102, 102);
    protected static final Color orange = new Color(255, 128, 0);
    protected static final Color red = new Color(255, 0, 0);
    protected static final Color lightGreen = new Color(204, 255, 102);
    protected static final Color banana = new Color(255, 255, 102);
    protected static final Color lemon = new Color(255, 255, 0);
    protected static final Color spindrift = new Color(102, 255, 204);
    protected static final Color flora = new Color(102, 255, 102);
    protected static final Color seaFoam = new Color(0, 255, 128);
    protected static final Color lime = new Color(128, 255, 0);
    protected static final Color green = new Color(0, 255, 0);
    protected static final Color sky = new Color(102, 204, 255);
    protected static final Color ice = new Color(102, 255, 255);
    protected static final Color turquoise = new Color(0, 255, 255);
    protected static final Color lavender = new Color(204, 102, 255);
    protected static final Color orchid = new Color(102, 102, 255);
    protected static final Color aqua = new Color(0, 128, 255);
    //protected static final Color grape = new Color(128, 0, 255);
    protected static final Color lightPink = new Color(255, 111, 207);
    protected static final Color bubbleGum = new Color(255, 102, 255);
    protected static final Color magenta = new Color(255, 0, 255);
    protected static final Color hotPink = new Color(255, 0, 128);
    protected static final Color black = new Color(0, 0, 0);
    protected static final Color snow = new Color(255, 255, 255);

  private static final Color[] colorList = new Color[] { pastelGreen, pastelBlue, pastelYellow, pastelCyan, pastelPurple, pastelRed};
  private static int nextColorIndex = 0;
  private static LinkedHashMap<String, Color> colorMap;

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
  private static ArrayList<TaskView> taskViews;
  private static Course generalCourse;
  private static SpringLayout layout;
  private static JMenuBar menuBar;
  private static Container contentPane;
  private static boolean recording;
  private static MacroRecDialog macroRecDialog;

  private static boolean DEBUG = false;
  private static String CLASS = "TaskCommander";
  private static final String TASK_ENTRY = "Task Entry Mode";
  private static final String PLANNING = "Planning Board";

  private static void log(String message)
  {
    log(CLASS, message);
  }

  //Public Methods
    public static void setRecording(boolean value) { recording = value; }
    public static void setTitle(String value)
    {
      mainFrame.setTitle(CLASS + ": " + value);
    }
    public static Course getGeneralCourse() { return generalCourse; }
    public static void showNewCourseDialog()
    {
      CourseDialog dialog = new CourseDialog(mainFrame);
      dialog.setVisible(true);
      //showCourseDialog(null);
    }
    public static void showCourseDialog(final Course course)
    {
      CourseDialog dialog = new CourseDialog(mainFrame, course);
      dialog.setVisible(true);
    }
    public static void showNewTaskDialog()
    {
      showTaskDialog(null);
    }
    public static void showTaskDialog(final Task task)
    {
      TaskDialog dialog = new TaskDialog(mainFrame, task);
      dialog.setVisible(true);

      /*
      final boolean existingTask = !(task == null);
      JDialog dialog = new JDialog(mainFrame);
      dialog.setModalityType(ModalityType.APPLICATION_MODAL);
      dialog.setLocationRelativeTo(null);
      dialog.setSize(new Dimension(640, 480));

      final JTextField taskNameTextField, dueDateTextField, assignedDateTextField, priorityTextField, associatedCourseTextField;
      JLabel taskNameLabel, dueDateLabel, assignedDateLabel, priorityLabel, associatedCourseLabel;
      JPanel taskName, dueDate, assignedDate, priority, associatedCourse;

      Task temp;
      if (existingTask)
      {
        temp = task;
      }
      else
      {
        temp = new Task("", null);
      }

      taskNameLabel = new JLabel("Task Name:");
      taskNameTextField = new JTextField(temp.getName());
      taskName = new JPanel(new BorderLayout());
      taskName.add(taskNameLabel, BorderLayout.WEST);
      taskName.add(taskNameTextField, BorderLayout.CENTER);

      JPanel buttonPanel = new JPanel(new FlowLayout());
      JButton ok = new JButton("Ok");
      JButton cancel = new JButton("Cancel");
      final JDialog resultDialog = dialog;
      ok.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          if (existingTask)
          {
            log("Generate task edit command");
            Task editedTask = new Task("", null);
            editedTask.updateFrom(task);
            editedTask.setName(taskNameTextField.getText());
            Command command = new TaskEdit(task, editedTask);
            command.run();
            TaskCommander.addCommand(command);
          }
          else
          {
            log("Generate new task command");
            Task newTask = new Task(taskNameTextField.getText(), null);
            Command command = new TaskAddition(newTask);
            command.run();
            TaskCommander.addCommand(command);
          }
          resultDialog.dispose();
        }
      });
      cancel.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          resultDialog.dispose();
        }
      });
      buttonPanel.add(cancel);
      buttonPanel.add(ok);

      Container contentPane = dialog.getContentPane();
      contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

      // Add components to content pane
        contentPane.add(taskName);
        contentPane.add(buttonPanel);

      dialog.pack();
      dialog.setVisible(true);

      */
    }
    public static void removeTask(Task task)
    {
      TaskRemoval command = new TaskRemoval(task);
      command.run();
      TaskCommander.addCommand(command);
    }
    public static void registerTaskView(TaskView taskView) { taskViews.add(taskView); }
    public static ArrayList<TaskView> getRegisteredTaskViews() { return taskViews; }
    public static TaskEntryPanel getTaskEntryPanel() { return taskEntryPanel; }
    //public static Color getDefaultCourseColor() { return neutralColor; }
    public static Color getNextCourseColor()
    {
      return colorList[nextColorIndex++%6];
    }
    public static LinkedHashMap<String, Color> getColorMap() { return colorMap; }

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
        if (recording)
        {
          macroRecDialog.removeCommand(lastCommand);
        }
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
        if (recording)
        {
          macroRecDialog.addCommand(lastCommand);
        }
        lastCommand.redo();
        runCommands.push(lastCommand);
      }
    }

    public static void addCommand(Command command)
    {
      if (recording)
      {
        macroRecDialog.addCommand(command);
      }
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
      course.setColor(getNextCourseColor());
      addCourse(course, taskView);
    }

    public static void addCourse(Course course, TaskView taskView)
    {
      Command command = new CourseAddition(course, taskView);
      command.run();
      addCommand(command);
    }

    
    public static void addTask(String name, TaskView taskView)
    {
      if (DEBUG) log("Adding task with name: '" + name + "'");
      if (DEBUG) log("Get selected course");
      Course course = getSelectedCourse();
      if (course == null)
      {
        course = generalCourse;
      }
      if (DEBUG) log("Create Task object and add it");
      addTask(new Task(name, course), taskView);
    }

    public static void addTask(Task task, TaskView taskView)
    {
      if (DEBUG) log("Attempting to add Task object");
      if (DEBUG) log("Create the TaskAddition command object");
      Command command = new TaskAddition(task);
      if (DEBUG) log("Run the command");
      command.run();
      if (DEBUG) log("Add the command to the list of commands");
      addCommand(command);

      if (DEBUG) log("Revalidate and repaint taskEntryPanel");
      //taskEntryPanel.revalidate();
      //taskEntryPanel.repaint();
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
      //taskEntryPanel.revalidate();
      //taskEntryPanel.repaint();
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
      //taskEntryPanel.revalidate();
      //taskEntryPanel.repaint();
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
      UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
    }
    catch (UnsupportedLookAndFeelException e)
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
      catch (Exception ex)
      {
        ex.printStackTrace();
        System.exit(1);
      }
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

    colorMap = new LinkedHashMap<String, Color>();
    colorMap.put("Pastel Green", pastelGreen);
    colorMap.put("Pastel Blue", pastelBlue);
    colorMap.put("Pastel Yellow", pastelYellow);
    colorMap.put("Pastel Cyan", pastelCyan);
    colorMap.put("Pastel Purple", pastelPurple);
    colorMap.put("Pastel Red", pastelRed);
    colorMap.put("Melon", melon);
    colorMap.put("Salmon", salmon);
    colorMap.put("Orange", orange);
    colorMap.put("Red", red);
    colorMap.put("Light Green", lightGreen);
    colorMap.put("Banana", banana);
    colorMap.put("Lemon", lemon);
    colorMap.put("Spindrift", spindrift);
    colorMap.put("Flora", flora);
    colorMap.put("Sea Foam", seaFoam);
    colorMap.put("Lime", lime);
    colorMap.put("Green", green);
    colorMap.put("Sky", sky);
    colorMap.put("Ice", ice);
    colorMap.put("Turquoise", turquoise);
    colorMap.put("Lavender", lavender);
    colorMap.put("Orchid", orchid);
    colorMap.put("Aqua", aqua);
    //colorMap.put("Grape", grape);
    colorMap.put("Light Pink", lightPink);
    colorMap.put("Bubble Gum", bubbleGum);
    colorMap.put("Magenta", magenta);
    colorMap.put("Hot Pink", hotPink);
    colorMap.put("Black", black);
    colorMap.put("Snow", snow);

    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        createAndShowGUI();
      }
    });
  }






  // Private methods
    private static void addToToolbar(AbstractButton button, ImageIcon icon)
    {
      JButton temp = new JButton(icon);
      temp.setModel(button.getModel());
      toolBar.add(temp);
    }
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
            //taskEntryPanel.revalidate();
            //taskEntryPanel.repaint();
          }
        });
        editMenu.add(menuItem);
        addToToolbar(menuItem, new ImageIcon("images/Undo24.png"));

        menuItem = new JMenuItem("Redo", KeyEvent.VK_R);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.META_DOWN_MASK));
        menuItem.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            redo();
            //taskEntryPanel.revalidate();
            //taskEntryPanel.repaint();
          }
        });
        editMenu.add(menuItem);
        addToToolbar(menuItem, new ImageIcon("images/Redo24.png"));
        toolBar.addSeparator();

      //Build the view menu
        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic(KeyEvent.VK_V);

        ButtonGroup viewModes = new ButtonGroup();
        JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("Task Entry Mode");
        rbMenuItem.setSelected(false);
        rbMenuItem.setMnemonic(KeyEvent.VK_T);
        rbMenuItem.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            CardLayout c = (CardLayout)(mainContentPanel.getLayout());
            c.show(mainContentPanel, TASK_ENTRY);
            setTitle("Task Entry");
          }
        });
        viewModes.add(rbMenuItem);
        viewMenu.add(rbMenuItem);
        
        addToToolbar(rbMenuItem, new ImageIcon("images/TaskEntry24.png"));

        rbMenuItem = new JRadioButtonMenuItem("Planning Board");
        rbMenuItem.setSelected(true);
        rbMenuItem.setMnemonic(KeyEvent.VK_P);
        rbMenuItem.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            CardLayout c = (CardLayout)(mainContentPanel.getLayout());
            c.show(mainContentPanel, PLANNING);
            setTitle("Planning Board");
          }
        });
        viewModes.add(rbMenuItem);
        viewMenu.add(rbMenuItem);

        addToToolbar(rbMenuItem, new ImageIcon("images/PlanningBoard24.png"));


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
            recording = true;
            macroRecDialog = new MacroRecDialog();
            macroRecDialog.setVisible(true);
            //JFrame temp = new JFrame("Record... Window");
            //temp.setSize(new Dimension(480, 320));
            //temp.setLocationRelativeTo(null);
            //temp.setVisible(true);
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
        menuItem = new JMenuItem("Options...");
        menuItem.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            JFrame temp = new JFrame("Options Window");
            temp.setSize(new Dimension(480, 320));
            temp.setLocationRelativeTo(null);
            temp.setVisible(true);
          }
        });
        toolsMenu.add(menuItem);

      // Add menus to menuBar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(toolsMenu);
    }

    private static void configureComponents()
    {
      recording = false;
      macroRecDialog = null;
      contentPane = mainFrame.getContentPane();
      constantsPanel = new JPanel();
      mainContentPanel = new JPanel(new CardLayout());
      toolBar = new JToolBar();
      toolBar.setFloatable(true);
      toolBar.setBorderPainted(true);
      toolBar.setBorder(BorderFactory.createLineBorder(Color.black));


      taskEntryPanel = new TaskEntryPanel();
      planningBoard = new TaskPlanningPanel();
      input = new JTextField();

      taskViews = new ArrayList<TaskView>();
      registerTaskView(taskEntryPanel);
      registerTaskView(planningBoard);

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

      mainContentPanel.add(planningBoard, PLANNING);
      mainContentPanel.add(taskEntryPanel, TASK_ENTRY);

      contentPane.add(constantsPanel, BorderLayout.NORTH);
      contentPane.add(mainContentPanel, BorderLayout.CENTER);

      // Add general course
        generalCourse = new Course("General");
        Command command = new CourseAddition(generalCourse, taskEntryPanel);
        command.run();
    }
}
