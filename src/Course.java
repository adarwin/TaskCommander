/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.util.Date;

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
  String courseName, professor, building;
  int roomNumber;
  int[] startingTime;
  int[] endingTime;
  int courseNumber;
  double grade;

  public Course(String name)
  {
    courseName = name;
  }
}
