/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.awt.datatransfer.Transferable;

import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.MatteBorder;

import java.util.Calendar;
import java.util.ArrayList;

public class TaskWidget extends JPanel implements DragGestureListener
{
  private CustomCheckBox taskCheckBox;
  private JLabel courseLabel, dueDate;
  private Dimension minimumSize, preferredSize, maximumSize;
  private SpringLayout layout;
  private JButton notesButton;
  private Task task;
  private JPopupMenu rightClickMenu;
  private TransferHandler customTransferHandler;
  private DragSource dragSource;

  // Public Methods
    public void setSelected(boolean selected)
    {
      task.setSelected(selected);
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

    public void dragGestureRecognized(DragGestureEvent dge)
    {
      System.out.println("DRAG");
    }
    public void updateTaskInfo()
    {
      taskCheckBox.setText(task.getName());
      courseLabel.setText(task.getCourse().getName());
      //dueDate.setText(
      boolean completed = task.isCompleted();
      setCompleted(task.isCompleted());
    }
    public void setCompleted(boolean completed)
    {
      taskCheckBox.setSelected(completed);
      task.setCompleted(completed);
      dueDate.setEnabled(!completed);
      notesButton.setEnabled(!completed);
      courseLabel.setEnabled(!completed);
    }
    public void updateColor()
    {
      courseLabel.setBackground(task.getCourse().getColor());
    }
    /*
    public void setColor(Color color)
    {
      course.setColor(color);
      colorIndicator.setBackground(color);
      TaskCommander.getTaskEntryPanel().updateTaskColorsForCourse(course);
    }
    */
    public Task getTask() { return task; }
    public boolean isSelected() { return task.isSelected(); }

    public TaskWidget() { this("TaskNameHere", "CourseNameHere"); }
    public TaskWidget(String taskName) { this(taskName, "SomeCourseName"); }
    public TaskWidget(String taskName, String courseName) { this(new Task(taskName, new Course(courseName))); }
    public TaskWidget(Task task)
    {
      this.task = task;
      configureComponents();
      configureLayouts();
      addComponents();
      configurePopupMenu();
      addListeners();
      setCompleted(task.isCompleted());
    }


  // Private methods
    private void sendSelectionRequest(boolean selectionRequest)
    {
      TaskCommander.getTaskEntryPanel().setSelectedTaskWidget(this, selectionRequest);
      TaskCommander.getTaskEntryPanel().setSubTaskEntryEnabled(selectionRequest);
    }
    private void displayRightClickMenu(MouseEvent e)
    {
      rightClickMenu.show(this, e.getX(), e.getY());
    }


    private void configureComponents()
    {
      minimumSize = new Dimension(100, 60);
      preferredSize = new Dimension(200, 60);
      maximumSize = new Dimension(3000, 60);
      notesButton = new JButton("");
      notesButton.setIcon(new ImageIcon("images/Notes.jpg"));
      notesButton.setToolTipText("Click this button to add a note to this task.");
      taskCheckBox = new CustomCheckBox(task.getName());
      taskCheckBox.setToolTipText("<html>Click this checkbox to toggle this task<br>" +
                                  "between complete and incomplete.</html>");
      courseLabel = new JLabel(task.getCourse().getName());
      courseLabel.setToolTipText("This indicates the course this task is associated with.");
      int newX = courseLabel.getPreferredSize().width;
      int newY = courseLabel.getPreferredSize().height;
      courseLabel.setPreferredSize(new Dimension(newX+10, newY+10));
      courseLabel.setHorizontalAlignment(SwingConstants.CENTER);
      //System.out.println(newX + "   " + newY);
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
      dueDate.setToolTipText("This is the due date for this task.");
      setBorder(BorderFactory.createEtchedBorder());
      setPreferredSize(preferredSize);
      setMaximumSize(maximumSize);
      courseLabel.setOpaque(true);
      courseLabel.setBackground(task.getCourse().getColor());
      courseLabel.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));

      customTransferHandler = new TransferHandler()
      {
      };

      setTransferHandler(new TransferHandler()
      {
      });

      dragSource = DragSource.getDefaultDragSource();
    }

    private void configureLayouts()
    {
      layout = new SpringLayout();
      setLayout(layout);
      int margin = 2;

      layout.putConstraint(SpringLayout.NORTH, taskCheckBox, margin, SpringLayout.NORTH, this);
      layout.putConstraint(SpringLayout.WEST, taskCheckBox, margin, SpringLayout.WEST, this);
      layout.putConstraint(SpringLayout.SOUTH, taskCheckBox, -1*margin, SpringLayout.SOUTH, notesButton);

      layout.putConstraint(SpringLayout.NORTH, dueDate, margin, SpringLayout.SOUTH, taskCheckBox);
      layout.putConstraint(SpringLayout.SOUTH, dueDate, -1*margin, SpringLayout.SOUTH, this);
      layout.putConstraint(SpringLayout.WEST, dueDate, margin + 23, SpringLayout.WEST, this);

      layout.putConstraint(SpringLayout.NORTH, notesButton, margin, SpringLayout.NORTH, this);
      layout.putConstraint(SpringLayout.EAST, notesButton, -1*margin, SpringLayout.EAST, this);

      layout.putConstraint(SpringLayout.NORTH, courseLabel, margin, SpringLayout.SOUTH, notesButton);
      layout.putConstraint(SpringLayout.SOUTH, courseLabel, -1*margin, SpringLayout.SOUTH, this);
      layout.putConstraint(SpringLayout.EAST, courseLabel, -1*margin, SpringLayout.EAST, this);
    }

    private void addComponents()
    {
      add(taskCheckBox);
      add(courseLabel);
      add(dueDate);
      add(notesButton);
    }

    private void showTaskEditDialog()
    {
      TaskCommander.showTaskDialog(this.task);
    }
    private void configurePopupMenu()
    {
      rightClickMenu = new JPopupMenu();
      JMenuItem menuItem = new JMenuItem("Edit");
      menuItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          System.out.println("edit");
          showTaskEditDialog();
        }
      });
      rightClickMenu.add(menuItem);
      menuItem = new JMenuItem("View Details");
      menuItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          System.out.println("view details");
        }
      });
      menuItem = new JMenuItem("Change Course...");
      menuItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          System.out.println("Change Course");
        }
      });
      rightClickMenu.add(menuItem);
      menuItem = new JMenuItem("Delete");
      menuItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          System.out.println("delete");
          Command command = new TaskRemoval(task);
          command.run();
          TaskCommander.addCommand(command);
          //TaskCommander.removeTask(task);
        }
      });
      rightClickMenu.add(menuItem);
      rightClickMenu.pack();
    }

    private void addListeners()
    {
      addMouseListener(new MouseListener()
      {
        public void mouseClicked(MouseEvent e)
        {
          if (e.getClickCount() == 2)
          {
            showTaskEditDialog();
          }
        }
        public void mouseEntered(MouseEvent e)
        {
          /*
          if (e.getButton() == MouseEvent.BUTTON1)
          {
            //Select
            setSelected(!isSelected());
          }
          */
        }
        public void mouseExited(MouseEvent e) {}
        public void mousePressed(MouseEvent e)
        {
          if(e.getButton() == MouseEvent.BUTTON3)
          {
            displayRightClickMenu(e);
          }
          else if (e.getButton() == MouseEvent.BUTTON1)
          {
            //Select
            boolean select = !isSelected();
            sendSelectionRequest(select);
            //setSelected(select);
            ArrayList<TaskView> taskViews = TaskCommander.getRegisteredTaskViews();
            for (TaskView taskView : taskViews)
            {
              taskView.showSubTasksFor(select ? task : null);
            }
          }
        }
        public void mouseReleased(MouseEvent e) {}
      });

      taskCheckBox.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          Task newState = new Task("", null);
          newState.updateFrom(task);
          System.out.println("taskCheckBox selected? " + taskCheckBox.isSelected());
          newState.setCompleted(taskCheckBox.isSelected());
          TaskEdit command = new TaskEdit(task, newState);
          command.run();
          TaskCommander.addCommand(command);
          //setSelected(taskCheckBox.isSelected());
          //setCompleted(taskCheckBox.isSelected());
          /*
          dueDate.setEnabled(!taskCheckBox.isSelected());
          notesButton.setEnabled(!taskCheckBox.isSelected());
          courseLabel.setEnabled(!taskCheckBox.isSelected());
          */
        }
      });
    }
}
