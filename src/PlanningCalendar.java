/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

public class PlanningCalendar extends JPanel implements TaskView
{
  class Day extends JPanel
  {
    private int date;
    private JLabel dateLabel;
    public Day(Integer date)
    {
      this.date = date;
      setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
      dateLabel = new JLabel(date.toString());
      add(dateLabel);
    }
    public int getDate() { return date; }
  }

  Day[][] days;

  public PlanningCalendar()
  {
    days = new Day[6][8];
    Calendar cal = Calendar.getInstance();
    Integer dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    Integer dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
    Integer weekOfMonth = cal.get(Calendar.WEEK_OF_MONTH);
    Integer dayOfWeekInMonth = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    configureCalendar(cal.get(Calendar.DAY_OF_WEEK), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.WEEK_OF_MONTH), cal.get(Calendar.MONTH));
  }
  private void configureCalendar(int dayOfWeek, int dayOfMonth, int weekOfMonth, int month)
  {
    int originalDayOfWeek = dayOfWeek;
    int totalDays;
    switch (month)
    {
      case 0: totalDays = 31; break;
      case 1: totalDays = 28; break;
      case 2: totalDays = 31; break;
      case 3: totalDays = 30; break;
      case 4: totalDays = 31; break;
      case 5: totalDays = 30; break;
      case 6: totalDays = 31; break;
      case 7: totalDays = 31; break;
      case 8: totalDays = 30; break;
      case 9: totalDays = 31; break;
      case 10: totalDays = 30; break;
      case 11: totalDays = 31; break;
      default: totalDays = 0;
    }
    int dayCount = dayOfMonth;
    System.out.println("Start at week: " + weekOfMonth + " and day: " + dayOfWeek);
    for (int week = weekOfMonth; week > 0; week--)
    {
      for (int day = dayOfWeek; day > 0; day--)
      {
        if (dayCount > 0)
        {
          days[week][day] = new Day(dayCount--);
        }
      }
      dayOfWeek = 7;
    }
    dayCount = dayOfMonth;
    dayOfWeek = originalDayOfWeek;
    System.out.println("Start at week: " + weekOfMonth + " and day: " + dayOfWeek);
    System.out.println(totalDays);
    System.out.println(month);
    for (int week = weekOfMonth; week < 6; week++)
    {
      for (int day = dayOfWeek; day <  8; day++)
      {
        if (dayCount <= totalDays)
        {
          days[week][day] = new Day(dayCount++);
        }
      }
      dayOfWeek = 1;
    }
    for (Day[] value : days)
    {
      System.out.println("\r\n---------");
      for (Day day : value)
      {
        System.out.print(", " + (day == null ? day : day.getDate()));
      }
    }
  }
  public void addCourse(Course course)
  {
  }
  public void removeCourse(Course course)
  {
  }
  public void addTask(Task task)
  {
  }
  public void removeTask(Task task)
  {
  }
}
