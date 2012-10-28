/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.border.SoftBevelBorder;

public class CourseWidget extends JPanel
{
  private JCheckBox courseCheckBox;
  private Dimension minimumSize, preferredSize, maximumSize;
  private JButton colorButton;
  private Course course;
  private static JFrame colorFrame;

  private void setColor(Color color)
  {
    course.setColor(color);
    setBackground(color);
  }
  private void setSelected(boolean selected)
  {
    course.setSelected(selected);
    Color color = course.getColor();
    if (selected)
    {
      float h, s, b;
      float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
      setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]*.8f));
    }
    else
    {
      setBackground(color);
    }
  }

  private void showColorChooser()
  {
    final JColorChooser colorChooser = new JColorChooser(course.getColor());
    JColorChooser.createDialog(this,
                               "title",
                               true,
                               colorChooser,
                               new ActionListener()
                               {
                                 //okListener
                                 public void actionPerformed(ActionEvent e)
                                 {
                                   setColor(colorChooser.getColor());
                                 }
                               },
                               new ActionListener()
                               {
                                 //cancelListener
                                 public void actionPerformed(ActionEvent e)
                                 {
                                 }
                               });
    dialog.setVisible(true);
  }
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



    // Add Listeners
      courseCheckBox.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          setSelected(courseCheckBox.isSelected());
        }
      });
      colorButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          showColorChooser();
        }
      });

    setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
    setPreferredSize(preferredSize);
    setMaximumSize(maximumSize);
    add(courseCheckBox, BorderLayout.WEST);
    add(colorButton, BorderLayout.EAST);

    setBackground(course.getColor());
  }
}
