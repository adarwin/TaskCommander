/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.Calendar;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

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
      //add(new TaskWidget("test"));
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

  Day[][] daysOfMonth;
  Day[] daysOfWeek;
  Day monthToday, weekToday;
  JPanel monthView, weekView;

  public PlanningCalendar()
  {
    JTabbedPane tabbedPane = new JTabbedPane();
    monthView = new JPanel();
    weekView = new JPanel();
    monthView.setLayout(new GridBagLayout());
    weekView.setLayout(new GridBagLayout());
    tabbedPane.add("Month", monthView);
    tabbedPane.add("Week", weekView);
    setLayout(new BorderLayout());
    GridBagConstraints layoutConstraints = new GridBagConstraints();
    daysOfMonth = new Day[6][8]; //Create extra row and column for index by 1 purposes
    daysOfWeek = new Day[8]; //Create extra column for index by 1 purposes
    Calendar cal = Calendar.getInstance();
    Integer dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    Integer dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
    Integer weekOfMonth = cal.get(Calendar.WEEK_OF_MONTH);
    Integer dayOfWeekInMonth = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    add(tabbedPane, BorderLayout.CENTER);
    setToday(cal.get(Calendar.DAY_OF_MONTH));
    setToday(cal.get(Calendar.DAY_OF_MONTH));
    configureMonthView(cal.get(Calendar.DAY_OF_WEEK), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.WEEK_OF_MONTH), cal.get(Calendar.MONTH));
    configureWeekView(cal.get(Calendar.DAY_OF_WEEK), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH));
  }
  private int numberOfDaysInMonth(int month)
  {
    int daysInMonth;
    switch (month)
    {
      case 0: daysInMonth = 31; break;
      case 1: daysInMonth = 28; break;
      case 2: daysInMonth = 31; break;
      case 3: daysInMonth = 30; break;
      case 4: daysInMonth = 31; break;
      case 5: daysInMonth = 30; break;
      case 6: daysInMonth = 31; break;
      case 7: daysInMonth = 31; break;
      case 8: daysInMonth = 30; break;
      case 9: daysInMonth = 31; break;
      case 10: daysInMonth = 30; break;
      case 11: daysInMonth = 31; break;
      default: daysInMonth = 0;
    }
    return daysInMonth;
  }
  private String dayOfWeek(int dayOfWeek)
  {
    String day;
    switch (dayOfWeek)
    {
      case 1: day = "Sun"; break;
      case 2: day = "Mon"; break;
      case 3: day = "Tues"; break;
      case 4: day = "Wed"; break;
      case 5: day = "Thurs"; break;
      case 6: day = "Fri"; break;
      case 7: day = "Sat"; break;
      default: day = "";
    }
    return day;
  }
  private void setToday(int dayOfMonth)
  {
    weekToday = new Day(dayOfMonth);
    monthToday = new Day(dayOfMonth);
    weekToday.setBackground(TaskCommander.pastelBlue);
    monthToday.setBackground(TaskCommander.pastelBlue);
  }
  private void configureWeekView(int dayOfWeek, int dayOfMonth, int month)
  {
    int daysInMonth = numberOfDaysInMonth(month);
    int count = dayOfMonth-1;
    // Set up past days
      for (int day = dayOfWeek-1; day > 0; day--)
      {
        daysOfWeek[day] = new Day(count--);
      }
    
    daysOfWeek[dayOfWeek] = weekToday;
    count = dayOfMonth+1;

    // Set up future days
      for (int day = dayOfWeek+1; day < 8; day++)
      {
        daysOfWeek[day] = new Day(count++);
      }


    for (int i = 1; i < 8; i++)
    {
      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.BOTH;
      c.weightx = 0.5;
      c.weighty = 0;
      c.gridx = i;
      c.gridy = 0;
      c.gridheight = 1;
      String day = "";
      switch (i)
      {
        case 1: day = "Sun"; break;
        case 2: day = "Mon"; break;
        case 3: day = "Tues"; break;
        case 4: day = "Wed"; break;
        case 5: day = "Thurs"; break;
        case 6: day = "Fri"; break;
        case 7: day = "Sat"; break;
      }
      JLabel temp = new JLabel(day);
      temp.setHorizontalAlignment(SwingConstants.CENTER);
      weekView.add(temp, c);



      c = new GridBagConstraints();
      c.fill = GridBagConstraints.BOTH;
      c.weightx = 0.5;
      c.weighty = 0.5;
      c.gridx = i;
      c.gridy = 1;
      c.gridheight = 1;
      if (daysOfWeek[i] != null)
      {
        weekView.add(daysOfWeek[i], c);
      }
      else
      {
        //System.out.println(i);
      }
    }


  }
  private void configureMonthView(int dayOfWeek, int dayOfMonth, int weekOfMonth, int month)
  {
    int originalDayOfWeek = dayOfWeek;
    int totalDays = numberOfDaysInMonth(month);
    int dayCount = dayOfMonth;
    //System.out.println("Start at week: " + weekOfMonth + " and day: " + dayOfWeek);
    //Set today
      daysOfMonth[weekOfMonth][dayOfWeek--] = monthToday;
      dayCount--;
    for (int week = weekOfMonth; week > 0; week--)
    {
      for (int day = dayOfWeek; day > 0; day--)
      {
        if (dayCount > 0)
        {
          daysOfMonth[week][day] = new Day(dayCount--);
        }
      }
      dayOfWeek = 7;
    }
    dayCount = dayOfMonth;
    dayOfWeek = originalDayOfWeek+1;
    //System.out.println("Start at week: " + weekOfMonth + " and day: " + dayOfWeek);
    //System.out.println(totalDays);
    //System.out.println(month);
    for (int week = weekOfMonth; week < 6; week++)
    {
      for (int day = dayOfWeek; day <  8; day++)
      {
        if (dayCount <= totalDays)
        {
          daysOfMonth[week][day] = new Day(dayCount++);
        }
      }
      dayOfWeek = 1;
    }
    //System.out.println("| Su | Mo | Tu | We | Th | Fr | Sa |");
    //System.out.println("------------------------------------");
    //Add day titles
      for (int i = 1; i < 8; i++)
      {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0;
        c.gridx = i;
        c.gridy = 0;
        c.gridheight = 1;
        String day = "";
        switch (i)
        {
          case 1: day = "Sun"; break;
          case 2: day = "Mon"; break;
          case 3: day = "Tues"; break;
          case 4: day = "Wed"; break;
          case 5: day = "Thurs"; break;
          case 6: day = "Fri"; break;
          case 7: day = "Sat"; break;
        }
        JLabel temp = new JLabel(day);
        temp.setHorizontalAlignment(SwingConstants.CENTER);
        monthView.add(temp, c);
      }
    for (int i = 1; i < daysOfMonth.length; i++)
    {
      for (int j = 1; j < daysOfMonth[i].length; j++)
      {
        String value;
        Day dayToAdd;
        if (daysOfMonth[i][j] == null)
        {
          value = "  ";
          dayToAdd = new Day();
        }
        else
        {
          String orig = daysOfMonth[i][j].getDate().toString();
          value = orig.length() > 1 ? orig : "0" + orig;

          dayToAdd = daysOfMonth[i][j];
        }
        //System.out.print("| " + value + " ");

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = j;
        c.gridy = i;
        c.gridheight = 1;
        monthView.add(dayToAdd, c);
      }
      //System.out.println("|");
      //System.out.println("------------------------------------");
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
  public void updateTaskColors()
  {
    System.out.println("updateTaskColors() currently does nothing");
  }
  public void updateTaskInfo()
  {
    System.out.println("updateTaskInfo() currently does nothing");
  }
}
