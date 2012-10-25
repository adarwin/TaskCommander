/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.SoftBevelBorder;

public class CourseWidget extends JPanel
{
  private JCheckBox courseCheckBox;
  private Dimension minimumSize, preferredSize, maximumSize;
  private JButton colorButton;
  private Course course;

  public Course getCourse() { return course; }
  public CourseWidget()
  {
    this("CourseName");
  }
  public CourseWidget(String courseName)
  {
    this(new Course(courseName));
  }
  public CourseWidget(Course course)
  {
    this.course = course;
    setLayout(new BorderLayout());
    minimumSize = new Dimension(100, 20);
    preferredSize = new Dimension(200, 20);
    maximumSize = new Dimension(3000, 30);
    colorButton = new JButton("Color");
    colorButton.setPreferredSize(new Dimension(65, 30));
    courseCheckBox = new JCheckBox(course.getName());

    setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
    setPreferredSize(preferredSize);
    setMaximumSize(maximumSize);
    add(courseCheckBox, BorderLayout.WEST);
    add(colorButton, BorderLayout.EAST);
  }
}
