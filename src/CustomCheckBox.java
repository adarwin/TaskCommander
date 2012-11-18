/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomCheckBox extends JPanel
{
  JCheckBox checkBox;
  JLabel label;
  Color defaultColor;

  public CustomCheckBox(String name)
  {
    checkBox = new JCheckBox();
    label = new JLabel(name);
    defaultColor = Color.black;
    setLayout(new BorderLayout());
    add(checkBox, BorderLayout.WEST);
    add(label, BorderLayout.EAST);
    setOpaque(false);
    checkBox.setOpaque(false);
    checkBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        label.setEnabled(!checkBox.isSelected());
        /*
        if (checkBox.isSelected())
        {
          label.setForeground(Color.gray);
        }
        else
        {
          label.setForeground(defaultColor);
        }
        */
      }
    });
  }

  public void setText(String text)
  {
    label.setText(text);
  }
  public boolean isSelected() { return checkBox.isSelected(); }
  public void addActionListener(ActionListener listener)
  {
    checkBox.addActionListener(listener);
  }
}
