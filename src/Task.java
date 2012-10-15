/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.util.Date;

public class Task
{
  /*
  Need to keep track of:
  * Task Name
  * Due Date
  * Assigned Date
  * Associated Class
  * Priority Level
  * Steps to Completion
  * Notes
  */
  private String taskName;
  private Date dueDate, assignedDate;
  private int priority = 5; //Range of 0 to 10
  private String note;
  private Course associatedCourse;

  public Task(String name)
  {
    this(name, new Course("General"));
  }
  public Task(String name, Course course)
  {
    taskName = name;
    associatedCourse = new Course("General");
  }
  public Course getCourse() { return associatedCourse; }
  public void setCourse(Course newCourse) { associatedCourse = newCourse; }
  public String getName() { return taskName; }
  public void setName(String newName) { taskName = newName; }
  public Date getDueDate() { return dueDate; }
  public void setDueDate(Date newDate) { dueDate = newDate; }
  public int getPriority() { return priority; }
  public void setPriority(int value) { priority = value; }
  public String getNote() { return note; }
  public void setNote(String newNote) { note = newNote; }
}
