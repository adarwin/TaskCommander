/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JCheckBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

public class SubTaskWidget extends JPanel
{
  private SubTask subTask;
  private JCheckBox subTaskCheckBox;
  private SpringLayout layout;
  private JButton notesButton;
  private Dimension preferredSize, maximumSize;

  public SubTask getSubTask() { return subTask; }

  public SubTaskWidget(SubTask subTask)
  {
    //super();
    this.subTask = subTask;
    layout = new SpringLayout();
    setLayout(layout);
    //minimumSize = new Dimension(100, 50);
    preferredSize = new Dimension(200, 50);
    maximumSize = new Dimension(3000, 50);
    notesButton = new JButton("");
    notesButton.setIcon(new ImageIcon("images/Notes.jpg"));
    subTaskCheckBox = new JCheckBox(subTask.getName());
    //courseLabel = new JLabel(task.getCourse().getName());
    //Calendar cal = Calendar.getInstance();
    //Integer day = cal.get(Calendar.DAY_OF_WEEK);
    //String dayString;
    //switch (day)
    //{
      //case 1: dayString = "Sunday"; break;
      //case 2: dayString = "Monday"; break;
      //case 3: dayString = "Tuesday"; break;
      //case 4: dayString = "Wednesday"; break;
      //case 5: dayString = "Thursday"; break;
      //case 6: dayString = "Friday"; break;
      //case 7: dayString = "Saturday"; break;
      //default: dayString = "Unknown";
    //}
    //dueDate = new JLabel(dayString);
    setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
    //setMinimumSize(minimumSize);
    setPreferredSize(preferredSize);
    setMaximumSize(maximumSize);
    add(subTaskCheckBox);
    //add(courseLabel);
    //add(dueDate);
    add(notesButton);

    setBackground(subTask.getTask().getCourse().getColor());

    int margin = 2;

    layout.putConstraint(SpringLayout.NORTH, subTaskCheckBox, 0, SpringLayout.NORTH, this);
    layout.putConstraint(SpringLayout.WEST, subTaskCheckBox, 0, SpringLayout.WEST, this);

    //layout.putConstraint(SpringLayout.NORTH, courseLabel, 0, SpringLayout.SOUTH, taskCheckBox);
    //layout.putConstraint(SpringLayout.WEST, courseLabel, 27, SpringLayout.WEST, this);

    //layout.putConstraint(SpringLayout.NORTH, dueDate, margin, SpringLayout.NORTH, this);
    //layout.putConstraint(SpringLayout.EAST, dueDate, -1*margin, SpringLayout.EAST, this);

    layout.putConstraint(SpringLayout.NORTH, notesButton, 0, SpringLayout.NORTH, this);
    layout.putConstraint(SpringLayout.EAST, notesButton, 0, SpringLayout.EAST, this);

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
