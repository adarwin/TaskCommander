/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class TaskCommander
{
  private static Stack<Command> runCommands;
  private static Stack<Command> undonCommands;

  public static void main(String[] args)
  {
    JFrame mainFrame = new JFrame("TaskCommander");
    mainFrame.setPreferredSize(new Dimension(800, 600));
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    addComponentsToFrame(mainFrame);
    mainFrame.pack();
    mainFrame.setLocationRelativeTo(null);
    mainFrame.setVisible(true);
  }

  private static void addComponentsToFrame(JFrame mainFrame)
  {
    Container contentPane = mainFrame.getContentPane();
    JMenuBar menuBar = new JMenuBar();
    menuBar.setVisible(true);
    mainFrame.add(menuBar);
  }

  private static void undoLastCommand()
  {
    Command lastCommand = runCommands.pop();
    lastCommand.undo();
    undonCommands.push(lastCommand);
  }

  private static void redoLastCommand()
  {
    Command lastCommand = undonCommands.pop();
    lastCommand.redo();
    runCommands.push(lastCommand);
  }

  public static void addCommand(Command command)
  {
    if (runCommands == null)
    {
      runCommands = new Stack<Command>();
    }
    command.run();
    runCommands.push(command);
  }

  public static Stack<Command> getRunCommands() { return runCommands; }
  public static Stack<Command> getUndonCommands() { return undonCommands; }
}
