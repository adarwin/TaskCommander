/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.util.ArrayList;
import java.util.Calendar;
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
  private Calendar startingTime;
  private Calendar endingTime;
  private int courseNumber;
  private double grade;
  private Color color;

  private ArrayList<Object> fields;

  private boolean selected;

  public Course(String name)
  {
    courseName = name;
    selected = false;
    color = TaskCommander.getNextCourseColor();
  }
  public void updateFrom(Course newState)
  {
    courseName = newState.getName();
    professor = newState.getProfessorName();
    building = newState.getBuildingName();
    roomNumber = newState.getRoomNumber();
    startingTime = newState.getStartingTime();
    endingTime = newState.getEndingTime();
    courseNumber = newState.getCourseNumber();
    grade = newState.getGrade();
    color = newState.getColor();
  }

  public String getName() { return courseName; }
  public void setName(String newName) { courseName = newName; }

  public String getProfessorName() { return professor; }
  public void setProfessorName(String name) { professor = name; }
  public String getBuildingName() { return building; }
  public void setBuildingName(String name) { building = name; }
  public int getRoomNumber() { return roomNumber; }
  public void setRoomNumber(int value) { roomNumber = value; }
  public Calendar getStartingTime() { return startingTime; }
  public Calendar getEndingTime() { return endingTime; }
  public int getCourseNumber() { return courseNumber; }
  public Double getGrade() { return grade; }

  public Color getColor() { return color; }
  public void setColor(Color color) { this.color = color; }

  public boolean isSelected() { return selected; }
  public void setSelected(boolean value) { selected = value; }
}
