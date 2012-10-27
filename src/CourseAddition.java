/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.Container;
import java.util.ArrayList;

public class CourseAddition implements Command
{
  //private String courseName;
  private TaskView taskViewToUpdate;
  private Course course;
  private boolean DEBUG = true;
  private String CLASS = "CourseAddition";

  private void log(String message)
  {
    TaskCommander.log(CLASS, message);
  }

  public CourseAddition(Course course, TaskView taskViewToUpdate)
  {
    this.course = course;
    //this.courseName = courseName;
    this.taskViewToUpdate = taskViewToUpdate;
  }

  public void run()
  {
    if (DEBUG) log("Attempting to run CourseAddition command");
    //course = new Course(courseName);
    if (DEBUG) log("Get courses from TaskCommander and add this course to the list");
    TaskCommander.getCourses().add(course);
    if (taskViewToUpdate != null)
    {
      if (DEBUG) log("Add course to taskViewToUpdate");
      taskViewToUpdate.addCourse(course);
      if (DEBUG) log("Revalidate and repaint");
      taskViewToUpdate.revalidate();
      taskViewToUpdate.repaint();
    }
    else
    {
      if (DEBUG) log("taskViewToUpdate == null, so stop execution here");
    }
  }
  public void undo()
  {
    if (DEBUG) log("Attempting to undo this CourseAddition");
    if (DEBUG) log("Get list of courses from TaskCommander and remove this course from the list");
    TaskCommander.getCourses().remove(course);
    if (taskViewToUpdate != null)
    {
      if (DEBUG) log("Remove course from taskViewToUpdate");
      taskViewToUpdate.removeCourse(course);
      //taskViewToUpdate.revalidate();
      if (DEBUG) log("Repaint");
      taskViewToUpdate.repaint();
    }
    else
    {
      if (DEBUG) log("taskViewToUpdate == null, so stop execution here");
    }
  }
  public void redo()
  {
    if (DEBUG) log("Attempting to redo this CourseAddition");
    run();
  }
}
