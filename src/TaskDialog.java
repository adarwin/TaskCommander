/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;

public class TaskDialog extends JDialog
{
  private Task task;
  final boolean existingTask;
  JTextField taskNameTextField, dueDateTextField, assignedDateTextField, priorityTextField, associatedCourseTextField;
  JLabel taskNameLabel, dueDateLabel, assignedDateLabel, priorityLabel, associatedCourseLabel;
  JPanel taskName, dueDate, assignedDate, priority, associatedCourse, buttonPanel;
  JButton okButton, cancelButton;
  private final boolean DEBUG = true;
  private final String CLASS = "TaskDialog";

  private void log(String message)
  {
    TaskCommander.log(CLASS, message);
  }



  public TaskDialog(Frame owner)
  {
    this(owner, null);
  }

  public TaskDialog(Frame owner, Task task)
  {
    super(owner);
    if (task == null)
    {
      task = new Task("New Task", null);
      existingTask = false;
    }
    else
    {
      existingTask = true;
    }
    this.task = task;

    Container contentPane = getContentPane();
    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

    setModalityType(ModalityType.APPLICATION_MODAL);
    setSize(new Dimension(640, 480));
    setLocationRelativeTo(null);

    configureComponents();
    addComponents();
    addListeners();
    pack();
  }

  private void configureComponents()
  {
    taskNameLabel = new JLabel("Task Name:");
    taskNameTextField = new JTextField(task.getName());
    taskName = createFieldPanel(taskNameLabel, taskNameTextField);

    buttonPanel = new JPanel(new FlowLayout());
    okButton = new JButton("Ok");
    cancelButton = new JButton("Cancel");
    buttonPanel.add(cancelButton);
    buttonPanel.add(okButton);
  }
  private void addComponents()
  {
    Container contentPane = getContentPane();
    contentPane.add(taskName);
    contentPane.add(buttonPanel);
  }
  private void addListeners()
  {
    okButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        if (existingTask)
        {
          log("Generate task edit command");
          Task editedTask = new Task("", null);
          editedTask.updateFrom(task);
          editedTask.setName(taskNameTextField.getText());
          Command command = new TaskEdit(task, editedTask);
          command.run();
          TaskCommander.addCommand(command);
        }
        else
        {
          log("Generate new task command");
          Task newTask = new Task(taskNameTextField.getText(), TaskCommander.getGeneralCourse());
          Command command = new TaskAddition(newTask);
          command.run();
          TaskCommander.addCommand(command);
        }
        dispose();
      }
    });
    cancelButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        dispose();
      }
    });
  }
  private JPanel createFieldPanel(JLabel label, JComponent component)
  {
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(label, BorderLayout.WEST);
    panel.add(component, BorderLayout.CENTER);
    return panel;
  }
}
