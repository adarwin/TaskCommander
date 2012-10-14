/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.util.List;

public class Macro
{
  private List<Command> commands;
  public void addCommand(Command command)
  {
    commands.add(command);
  }
  public void removeCommand(Command command)
  {
    commands.remove(command);
  }
  public void run()
  {
    //Run all commands
  }
}
