/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.Dimension;
import java.util.Calendar;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class TaskWidget extends JPanel
{
  private JCheckBox task;
  private JLabel courseLabel, dueDate;
  private Dimension preferredSize;
  private SpringLayout layout;
  private JButton notesButton;
  public TaskWidget(String taskName)
  {
    this(taskName, "SomeCourseName");
  }
  public TaskWidget(String taskName, String course)
  {
    layout = new SpringLayout();
    setLayout(layout);
    preferredSize = new Dimension(300, 50);
    notesButton = new JButton("Notes");
    task = new JCheckBox(taskName);
    courseLabel = new JLabel(course);
    Calendar cal = Calendar.getInstance();
    Integer day = cal.get(Calendar.DAY_OF_WEEK);
    String dayString;
    switch (day)
    {
      case 1: dayString = "Sunday"; break;
      case 2: dayString = "Monday"; break;
      case 3: dayString = "Tuesday"; break;
      case 4: dayString = "Wednesday"; break;
      case 5: dayString = "Thursday"; break;
      case 6: dayString = "Friday"; break;
      case 7: dayString = "Saturday"; break;
      default: dayString = "Unknown";
    }
    dueDate = new JLabel(dayString);
    setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
    setPreferredSize(preferredSize);
    add(task);
    add(courseLabel);
    add(dueDate);
    add(notesButton);

    layout.putConstraint(SpringLayout.NORTH, task, 0, SpringLayout.NORTH, this);
    layout.putConstraint(SpringLayout.WEST, task, 0, SpringLayout.WEST, this);

    layout.putConstraint(SpringLayout.NORTH, courseLabel, 0, SpringLayout.SOUTH, task);
    layout.putConstraint(SpringLayout.WEST, courseLabel, 28, SpringLayout.WEST, this);

    layout.putConstraint(SpringLayout.NORTH, dueDate, 0, SpringLayout.NORTH, this);
    layout.putConstraint(SpringLayout.EAST, dueDate, 0, SpringLayout.EAST, this);

    layout.putConstraint(SpringLayout.NORTH, notesButton, 0, SpringLayout.NORTH, this);
    layout.putConstraint(SpringLayout.EAST, notesButton, 0, SpringLayout.WEST, dueDate);

  }
}
