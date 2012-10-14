/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TaskWidgetDivider extends JPanel
{
  private JLabel title;
  public TaskWidgetDivider(String text)
  {
    super();
    title = text == null ? new JLabel("TaskWidgetDivider") : new JLabel(text);
    add(title, BorderLayout.CENTER);
  }
}
