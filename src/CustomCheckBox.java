/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.event.ActionListener;

public class CustomCheckBox extends JPanel
{
  JCheckBox checkBox;
  JLabel label;

  public CustomCheckBox(String name)
  {
    checkBox = new JCheckBox();
    label = new JLabel(name);
    setLayout(new BorderLayout());
    add(checkBox, BorderLayout.WEST);
    add(label, BorderLayout.EAST);
    setOpaque(false);
    checkBox.setOpaque(false);
  }

  public boolean isSelected() { return checkBox.isSelected(); }
  public void addActionListener(ActionListener listener)
  {
    checkBox.addActionListener(listener);
  }
}
