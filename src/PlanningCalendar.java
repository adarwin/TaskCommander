/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.Calendar;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

public class PlanningCalendar extends JPanel implements TaskView
{
  class Day extends JPanel
  {
    private Integer date;
    private JLabel dateLabel;
    public Day()
    {
      setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
      setPreferredSize(new Dimension(50, 50));
      setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
      dateLabel = new JLabel("");
      add(dateLabel);
    }
    public Day(Integer date)
    {
      this();
      setDate(date);
    }
    public Integer getDate() { return date; }
    public void setDate(Integer date)
    {
      this.date = date;
      dateLabel.setText(date.toString());
    }
  }

  Day[][] days;

  public PlanningCalendar()
  {
    setLayout(new GridBagLayout());
    GridBagConstraints layoutConstraints = new GridBagConstraints();
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
    System.out.println("| Su | Mo | Tu | We | Th | Fr | Sa |");
    System.out.println("------------------------------------");
    for (int i = 1; i < days.length; i++)
    {
      for (int j = 1; j < days[i].length; j++)
      {
        String value;
        Day dayToAdd;
        if (days[i][j] == null)
        {
          value = "  ";
          dayToAdd = new Day();
        }
        else
        {
          String orig = days[i][j].getDate().toString();
          value = orig.length() > 1 ? orig : "0" + orig;

          dayToAdd = days[i][j];
        }
        System.out.print("| " + value + " ");

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = j;
        c.gridy = i;
        add(dayToAdd, c);
      }
      System.out.println("|");
      System.out.println("------------------------------------");
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
  public void addSubTask(SubTask subTask)
  {
  }
  public void removeSubTask(SubTask subTask)
  {
  }
}
