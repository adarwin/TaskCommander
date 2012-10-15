/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.util.Calendar;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.border.SoftBevelBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class TaskWidget extends JPanel
{
  private JCheckBox taskCheckBox;
  private JLabel courseLabel, dueDate;
  private Dimension minimumSize, preferredSize, maximumSize;
  private SpringLayout layout;
  private JButton notesButton;
  private Task task;

  public TaskWidget()
  {
    this("TaskNameHere", "CourseNameHere");
  }
  public TaskWidget(String taskName)
  {
    this(taskName, "SomeCourseName");
  }
  public TaskWidget(String taskName, String course)
  {
    super();
    task = new Task(taskName);
    layout = new SpringLayout();
    setLayout(layout);
    minimumSize = new Dimension(100, 50);
    preferredSize = new Dimension(200, 50);
    maximumSize = new Dimension(3000, 50);
    notesButton = new JButton("");
    notesButton.setIcon(new ImageIcon("images/Notes.jpg"));
    taskCheckBox = new JCheckBox(taskName);
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
    //setMinimumSize(minimumSize);
    setPreferredSize(preferredSize);
    setMaximumSize(maximumSize);
    add(taskCheckBox);
    add(courseLabel);
    add(dueDate);
    add(notesButton);

    int margin = 2;

    layout.putConstraint(SpringLayout.NORTH, taskCheckBox, 0, SpringLayout.NORTH, this);
    layout.putConstraint(SpringLayout.WEST, taskCheckBox, 0, SpringLayout.WEST, this);

    layout.putConstraint(SpringLayout.NORTH, courseLabel, 0, SpringLayout.SOUTH, taskCheckBox);
    layout.putConstraint(SpringLayout.WEST, courseLabel, 27, SpringLayout.WEST, this);

    layout.putConstraint(SpringLayout.NORTH, dueDate, margin, SpringLayout.NORTH, this);
    layout.putConstraint(SpringLayout.EAST, dueDate, -1*margin, SpringLayout.EAST, this);

    layout.putConstraint(SpringLayout.NORTH, notesButton, 0, SpringLayout.NORTH, this);
    layout.putConstraint(SpringLayout.EAST, notesButton, 0, SpringLayout.WEST, dueDate);

    addMouseListener(new MouseListener()
    {
      public void mouseClicked(MouseEvent e) {}
      public void mouseEntered(MouseEvent e) {}
      public void mouseExited(MouseEvent e) {}
      public void mousePressed(MouseEvent e)
      {
        if(e.getButton() == MouseEvent.BUTTON3)
        {
          //Open right-click menu
          System.out.println("Open right-click menu");
        }
      }
      public void mouseReleased(MouseEvent e) {}
    });

  }
}
