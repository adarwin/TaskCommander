/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.util.Date;
import java.awt.Color;

public class Course
{
  /*
  Need to keep track of:
  * Course Name
  * Associated Professor
  * Location (Building)
  * Room Number
  * Starting Time
  * Ending Time
  * Course Number (CRN)
  * Current Grade
  * Grading Policy
  */
  private String courseName, professor, building;
  private int roomNumber;
  private int[] startingTime;
  private int[] endingTime;
  private int courseNumber;
  private double grade;
  private Color color;

  private boolean selected;

  public Course(String name)
  {
    courseName = name;
    selected = false;
    color = TaskCommander.getDefaultCourseColor();
  }
  public Color getColor() { return color; }
  public void setColor(Color color) { this.color = color; }
  public String getName() { return courseName; }
  public void setName(String newName) { courseName = newName; }
  public boolean isSelected() { return selected; }
  public void setSelected(boolean value) { selected = value; }
}
