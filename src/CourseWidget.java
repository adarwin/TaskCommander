/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

public class CourseWidget extends JPanel
{
  // Stored Objects
    //private JCheckBox courseCheckBox;
    private JLabel courseLabel;
    private Dimension minimumSize, preferredSize, maximumSize;
    //private JButton colorButton;
    private Course course;
    private JPanel colorIndicator;
    private SpringLayout layout;
    private JPopupMenu rightClickMenu;
    private static JFrame colorFrame;


  // Public Methods
    public void updateCourseInfo()
    {
      courseLabel.setText(course.getName());
      colorIndicator.setBackground(course.getColor());
    }
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
      configureComponents();
      configurePopupMenu();
      configureLayout();
      addListeners();
      addComponents();
    }

    public Course getCourse() { return course; }



  // Helper/Private Methods
    private void addToColorMenu(JMenu colorMenu, Color color, String colorString)
    {
      JMenuItem menuItem = new JMenuItem();
      menuItem.setText("");
      menuItem.setLayout(new BorderLayout());
      JPanel colorPanel = new JPanel();
      colorPanel.setPreferredSize(new Dimension(25, 20));
      JLabel colorLabel = new JLabel(" " + colorString);
      colorLabel.setForeground(Color.white);
      colorLabel.setHorizontalTextPosition(JLabel.LEFT);
      colorPanel.setBackground(color);
      menuItem.add(colorPanel, BorderLayout.WEST);
      menuItem.add(colorLabel, BorderLayout.CENTER);
      Dimension size = menuItem.getPreferredSize();
      menuItem.setPreferredSize(new Dimension(120, 20));
      final Color temp = color;
      menuItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          Course newState = new Course("");
          newState.updateFrom(course);
          newState.setColor(temp);
          CourseEdit courseEdit = new CourseEdit(course, newState);
          TaskCommander.addCommand(courseEdit);
          courseEdit.run();
          //setColor(temp);
        }
      });
      colorMenu.add(menuItem);
    }
    private void displayRightClickMenu(MouseEvent e)
    {
      rightClickMenu.show(this, e.getX(), e.getY());
    }
    private void showEditWindow()
    {
      TaskCommander.showCourseDialog(this.course);
    }
    private void configurePopupMenu()
    {
      rightClickMenu = new JPopupMenu();
      JMenuItem menuItem = new JMenuItem("Edit");
      menuItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          System.out.println("CourseWidget edit");
          showEditWindow();
        }
      });
      rightClickMenu.add(menuItem);
      menuItem = new JMenuItem("Rename");
      menuItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          System.out.println("Rename...");
        }
      });
      rightClickMenu.add(menuItem);
      //System.out.println(menuItem.getPreferredSize().height);

      JMenu colorMenu = new JMenu("Color");
      LinkedHashMap<String, Color> colorMap = TaskCommander.getColorMap();
      Set<String> keySet = colorMap.keySet();
      for (String key : keySet)
      {
        addToColorMenu(colorMenu, colorMap.get(key), key);
      }
      /*
      addToColorMenu(colorMenu, TaskCommander.pastelBlue, "Pastel Blue");
      addToColorMenu(colorMenu, TaskCommander.pastelGreen, "Pastel Green");
      addToColorMenu(colorMenu, TaskCommander.pastelYellow, "Pastel Yellow");
      addToColorMenu(colorMenu, TaskCommander.pastelRed, "Pastel Red");
      */
      rightClickMenu.add(colorMenu);

      menuItem = new JMenuItem("Delete");
      menuItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          System.out.println("CourseWidget Delete");
        }
      });
      rightClickMenu.add(menuItem);
      rightClickMenu.pack();
    }
    private void configureComponents()
    {
      colorIndicator = new JPanel();
      colorIndicator.setPreferredSize(new Dimension(30, 0));
      colorIndicator.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
      colorIndicator.setBackground(course.getColor());
      layout = new SpringLayout();
      setLayout(layout);
      //minimumSize = new Dimension(100, 20);
      preferredSize = new Dimension(200, 30);
      maximumSize = new Dimension(3000, 30);
      //colorButton = new JButton("Color");
      //colorButton.setPreferredSize(new Dimension(65, 30));
      //courseCheckBox = new JCheckBox(course.getName());
      courseLabel = new JLabel(course.getName());
      setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
      setPreferredSize(preferredSize);
      setMaximumSize(maximumSize);
      //setBackground(course.getColor());
    }


    private void configureLayout()
    {
      layout.putConstraint(SpringLayout.NORTH, colorIndicator, 0, SpringLayout.NORTH, this);
      layout.putConstraint(SpringLayout.SOUTH, colorIndicator, 0, SpringLayout.SOUTH, this);
      layout.putConstraint(SpringLayout.WEST, colorIndicator, 0, SpringLayout.WEST, this);
      layout.putConstraint(SpringLayout.NORTH, courseLabel, 0, SpringLayout.NORTH, this);
      layout.putConstraint(SpringLayout.SOUTH, courseLabel, 0, SpringLayout.SOUTH, this);
      layout.putConstraint(SpringLayout.WEST, courseLabel, 5, SpringLayout.EAST, colorIndicator);
      //layout.putConstraint(SpringLayout.NORTH, colorButton, 0, SpringLayout.NORTH, this);
      //layout.putConstraint(SpringLayout.SOUTH, colorButton, 0 ,SpringLayout.SOUTH, this);
      //layout.putConstraint(SpringLayout.EAST, colorButton, 0, SpringLayout.EAST, this);
    }


    private void addListeners()
    {
      addMouseListener(new MouseListener()
      {
        public void mouseClicked(MouseEvent e)
        {
        }
        public void mouseEntered(MouseEvent e)
        {
        }
        public void mouseExited(MouseEvent e)
        {
        }
        public void mousePressed(MouseEvent e)
        {
          setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
          if (e.getButton() == MouseEvent.BUTTON3)
          {
            displayRightClickMenu(e);
          }
        }
        public void mouseReleased(MouseEvent e)
        {
          setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
          if (e.getButton() == MouseEvent.BUTTON1)
          {
            //Select
            boolean select = !isSelected();
            sendSelectionRequest(select);
            ArrayList<TaskView> taskViews = TaskCommander.getRegisteredTaskViews();
            for (TaskView taskView : taskViews)
            {
              taskView.showTasksFor(select ? course : null);
            }
          }
        }
      });

      /*
      courseCheckBox.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          setSelected(courseCheckBox.isSelected());
        }
      });
      */

      /*
      colorButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          showColorChooser();
        }
      });
      */
    }


    private void addComponents()
    {
      add(colorIndicator);
      add(courseLabel);
      //add(colorButton);
    }

  private void setColor(Color color)
  {
    course.setColor(color);
    colorIndicator.setBackground(color);
    /*
    ArrayList<TaskView> taskViews = TaskCommander.getRegisteredTaskViews();
    for (TaskView taskView : taskViews)
    {
      taskView.updateTaskColors();
    }
    */
    //TaskCommander.getTaskEntryPanel().updateTaskColors();
  }
  private boolean isSelected() { return course.isSelected(); }
  private void sendSelectionRequest(boolean selectionRequest)
  {
    TaskCommander.getTaskEntryPanel().setSelectedCourseWidget(this, selectionRequest);
    TaskCommander.getTaskEntryPanel().setTaskEntryEnabled(selectionRequest);
  }
  protected void setSelected(boolean selected)
  {
    course.setSelected(selected);
    Color color = course.getColor();
    if (selected)
    {
      setBackground(TaskCommander.selectionColor);
    }
    else
    {
      //setBackground(TaskCommander.neutralColor);
      setBackground(null);
    }
  }

  private void showColorChooser()
  {
    final JColorChooser colorChooser = new JColorChooser(course.getColor());
    JDialog dialog = JColorChooser.createDialog(this,
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

}
