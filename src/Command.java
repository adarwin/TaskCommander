/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

public interface Command
{
  public void run();
  public void undo();
  public void redo();
}
