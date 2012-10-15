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
    /*
    JMenuBar menuBar = new JMenuBar();
    menuBar.setVisible(true);
    mainFrame.add(menuBar);
    */
    taskEntryPanel = new TaskEntryPanel();
    contentPane.add(taskEntryPanel, BorderLayout.CENTER);
    JButton create = new JButton("Create New Task");
    JButton undo = new JButton("Undo");
    JButton redo = new JButton("Redo");
    input = new JTextField();
    contentPane.add(create, BorderLayout.SOUTH);
    contentPane.add(undo, BorderLayout.WEST);
    contentPane.add(redo, BorderLayout.EAST);
    contentPane.add(input, BorderLayout.NORTH);



    create.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        addTask();
        taskEntryPanel.revalidate();
        taskEntryPanel.repaint();
      }
    });
    undo.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        undo();
        taskEntryPanel.revalidate();
        taskEntryPanel.repaint();
      }
    });
    redo.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        redo();
        taskEntryPanel.revalidate();
        taskEntryPanel.repaint();
      }
    });
  }

  //Public Methods
    public static JFrame getFrame() { return frame; }
    public static void undo()
    {
      if (!runCommands.isEmpty())
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
      if(!undonCommands.isEmpty())
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
    }






}
