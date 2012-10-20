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
  private static JFrame frame;
  private static JTextField input;

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

  public static void createAndShowGUI()
  {
    JFrame frame = new JFrame("TaskCommander");
    frame.setPreferredSize(new Dimension(800, 600));
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
    constantsPanel.setLayout(new BorderLayout());

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
      viewModes.add(rbMenuItem);
      viewMenu.add(rbMenuItem);
      rbMenuItem = new JRadioButtonMenuItem("Planning Board");
      rbMenuItem.setMnemonic(KeyEvent.VK_P);
      viewModes.add(rbMenuItem);
      viewMenu.add(rbMenuItem);

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


    JToolBar toolBar = new JToolBar();
    toolBar.setFloatable(false);
    toolBar.setBorderPainted(true);
    //toolBar.setPreferredSize(new Dimension(100, 50));
    toolBar.add(new JButton("ToolbarButton"));


    taskEntryPanel = new TaskEntryPanel();
    JButton undo = new JButton("Undo");
    JButton redo = new JButton("Redo");
    input = new JTextField();

    constantsPanel.add(menuBar, BorderLayout.NORTH);
    constantsPanel.add(toolBar, BorderLayout.SOUTH);
    contentPane.add(constantsPanel, BorderLayout.NORTH);
    contentPane.add(taskEntryPanel, BorderLayout.CENTER);



  }

  //Public Methods
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
      if (runCommands == null)
      {
        runCommands = new Stack<Command>();
      }
      command.run();
      runCommands.push(command);
      if (undonCommands != null)
      {
        undonCommands.clear();
      }
    }

    public static Stack<Command> getRunCommands() { return runCommands; }
    public static Stack<Command> getUndonCommands() { return undonCommands; }

    public static void addTask()
    {
      addTask(input.getText());
    }
    public static void addTask(String name)
    {
      System.out.println("Adding new task");
      Command command = new TaskAddition(taskEntryPanel, new TaskWidget(name));
      command.run();
      addCommand(command);

      taskEntryPanel.revalidate();
      taskEntryPanel.repaint();
    }






}
