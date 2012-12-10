/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;

public class CourseDialog extends JDialog
{
  private Course course;
  final boolean existingCourse;
  JTextField courseName, professor, buildingName, roomNumber, startingTime, endingTime, courseNumber, gradeTextField;
  JLabel courseNameLabel, professorLabel, buildingLabel, roomNumberLabel, startingTimeLabel, endingTimeLabel, courseNumberLabel, gradeLabel;
  JPanel name, prof, building, room, start, end, crn, grade, buttonPanel;
  JButton okButton, cancelButton;
  private final boolean DEBUG = true;
  private final String CLASS = "CourseDialog";


  private void log(String message)
  {
    TaskCommander.log(CLASS, message);
  }

  public CourseDialog(Frame owner)
  {
    this(owner, null);
  }

  public CourseDialog(Frame owner, Course course)
  {
    super(owner);
    if (course == null)
    {
      course = new Course("New Course");
      existingCourse = false;
    }
    else
    {
      existingCourse = true;
    }
    this.course = course;

    Container contentPane = getContentPane();
    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

    setModalityType(ModalityType.APPLICATION_MODAL);
    setLocationRelativeTo(null);
    setSize(new Dimension(640, 480));

    configureComponents();
    addComponents();
    addListeners();
    pack();
    //setVisible(true);
  }

  private void configureComponents()
  {
    courseNameLabel = new JLabel("Course Name:");
    courseName = new JTextField(course.getName());
    name = createFieldPanel(courseNameLabel, courseName);

    professorLabel = new JLabel("Professor:");
    professor = new JTextField(course.getProfessorName());
    prof = createFieldPanel(professorLabel, professor);

    buildingLabel = new JLabel("Building:");
    buildingName = new JTextField(course.getBuildingName());
    building = createFieldPanel(buildingLabel, buildingName);

    buttonPanel = new JPanel(new FlowLayout());
    okButton = new JButton("Ok");
    cancelButton = new JButton("Cancel");
    buttonPanel.add(cancelButton);
    buttonPanel.add(okButton);
    /*
    roomNumberLabel = new JLabel("Room Number:");
    roomNumber = new JTextField(course.getRoomNumber());
    room = createFieldPanel(roomNumberLabel, roomNumber);

    startingTimeLabel = new JLabel("Starting Time:");
    startingTime = new JTextField(course.getStartingTime() == null ? "" : course.getStartingTime().toString());
    start = createFieldPanel(startingTimeLabel, startingTime);

    endingTimeLabel = new JLabel("Ending Time:");
    endingTime = new JTextField(course.getEndingTime() == null ? "" : course.getEndingTime().toString());
    end = createFieldPanel(endingTimeLabel, endingTime);

    courseNumberLabel = new JLabel("Course Number:");
    courseNumber = new JTextField(course.getCourseNumber());
    crn = createFieldPanel(courseNumberLabel, courseNumber);

    gradeLabel = new JLabel("Grade:");
    gradeTextField = new JTextField(course.getGrade().toString());
    grade = createFieldPanel(gradeLabel, gradeTextField);
    */
  }

  private void addComponents()
  {
    Container contentPane = getContentPane();
    contentPane.add(name);
    contentPane.add(prof);
    contentPane.add(building);
    contentPane.add(buttonPanel);
    /*
    add(room);
    add(start);
    add(end);
    add(crn);
    add(grade);
    */
  }

  private void addListeners()
  {
    okButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        if (existingCourse)
        {
          log("Generate course edit command");
          Course editedCourse = new Course("");
          editedCourse.updateFrom(course);
          editedCourse.setName(courseName.getText());
          editedCourse.setProfessorName(professor.getText());
          editedCourse.setBuildingName(buildingName.getText());
          //editedCourse.setRoomNumber(Integer.parseInt(roomNumber.getText()));
          Command command = new CourseEdit(course, editedCourse);
          command.run();
          TaskCommander.addCommand(command);
        }
        else
        {
          log("Generate asdnew course command");
          Course newCourse = new Course(courseName.getText());
          newCourse.setProfessorName(professor.getText());
          newCourse.setBuildingName(buildingName.getText());
          Command command = new CourseAddition(newCourse, TaskCommander.getTaskEntryPanel());
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
