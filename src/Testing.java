/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import javax.swing.*;
import java.awt.*;

public class Testing
{
  public static void main(String[] args)
  {
    JFrame frame = new JFrame("Testing Window");
    JSplitPane splitPane = new JSplitPane();
    JPanel left = new JPanel();
    SpringLayout layout = new SpringLayout();
    left.setLayout(layout);
    JPanel right = new JPanel();
    splitPane.setLeftComponent(left);
    splitPane.setRightComponent(right);
    TaskWidget task = new TaskWidget("TaskName");
    TaskWidget task2 = new TaskWidget("Task2");

    layout.putConstraint(SpringLayout.NORTH, task, 0, SpringLayout.NORTH, left);
    layout.putConstraint(SpringLayout.EAST, task, 0, SpringLayout.EAST, left);
    layout.putConstraint(SpringLayout.WEST, task, 0, SpringLayout.WEST, left);
    layout.putConstraint(SpringLayout.NORTH, task2, 0, SpringLayout.SOUTH, task);
    layout.putConstraint(SpringLayout.EAST, task2, 0, SpringLayout.EAST, left);
    layout.putConstraint(SpringLayout.WEST, task2, 0, SpringLayout.WEST, left);

    left.add(task, BorderLayout.CENTER);
    left.add(task2);
    frame.add(splitPane);
    frame.pack();
    frame.setVisible(true);
  }
}
