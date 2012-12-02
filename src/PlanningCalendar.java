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
    private Integer dayOfMonth;
    private JLabel dateLabel;
    private Calendar calendar;

    public Day(Calendar calendar)
    {
      this();
      setCalendar(calendar);
    }
    public Day()
    {
      setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
      setPreferredSize(new Dimension(50, 50));
      setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
      dateLabel = new JLabel("");
      add(dateLabel);
    }
    /*
    public Day(Integer dayOfMonth)
    {
      this();
      setDayOfMonth(dayOfMonth);
    }
    */
    public Integer getDayOfMonth() { return dayOfMonth; }
    public Calendar getCalendar() { return calendar; }
    public void setCalendar(Calendar calendar)
    {
      this.calendar = calendar;
      setDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
    }
    public void setDayOfMonth(Integer dayOfMonth)
    {
      this.dayOfMonth = dayOfMonth;
      dateLabel.setText(dayOfMonth.toString());
    }
  }

  Day[][] daysOfMonth;
  Day[] daysOfWeek;
  Day monthToday, weekToday, dayToday;
  Calendar today;
  JPanel monthView, weekView, dayView;
  JTabbedPane tabbedPane;

  private final boolean DEBUG = true;
  private final String CLASS = "PlanningCalendar";

  private void log(String message)
  {
    TaskCommander.log(CLASS, message);
  }

  // Public Methods
    public PlanningCalendar()
    {
      configureComponents();
      configureLayouts();
      addComponents();
      addListeners();
    }

    // v--- TaskView Interface Methods ---v
      public void addCourse(Course course)
      {
        if (DEBUG) log("addCourse(Course course) currently does nothing");;
      }
      public void removeCourse(Course course)
      {
        if (DEBUG) log("removeCourse(Course course) currently does nothing");
      }
      public void addTask(Task task)
      {
        if (DEBUG) log("addTask(Task task) currently does nothing");
      }
      public void removeTask(Task task)
      {
        if (DEBUG) log("removeTask(Task task) currently does nothing");
      }
      public void addSubTask(SubTask subTask)
      {
        if (DEBUG) log("addSubTask(SubTask subTask) currently does nothing");
      }
      public void removeSubTask(SubTask subTask)
      {
        if (DEBUG) log("removeSubTask(SubTask subTask) currently does nothing");
      }
      public void updateTaskColors()
      {
        if (DEBUG) log("updateTaskColors() currently does nothing");
      }
      public void updateTaskInfo()
      {
        if (DEBUG) log("updateTaskInfo() currently does nothing");
      }
      public void updateCourseInfo()
      {
        if (DEBUG) log("updateCourseInfo() currently does nothing");
      }
      public void showTasksFor(Course course)
      {
        if (DEBUG) log("showTasksFor(Course course) currently does nothing");
      }
    // ^--- TaskView Interface Methods ---^


  // Private Methods
    // Conversion Methods
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
    private void setMonth(String month)
    {
    }
    private void setMonth(Integer month)
    {
    }
    private void configureComponents()
    {
      today = Calendar.getInstance();
      setToday(today.get(Calendar.DAY_OF_MONTH));
      dayToday = new Day(today.get(Calendar.DAY_OF_MONTH));
      weekToday = new Day(today.get(Calendar.DAY_OF_MONTH));
      monthToday = new Day(dayOfMonth);
      dayToday.setBackground(TaskCommander.pastelBlue);
      weekToday.setBackground(TaskCommander.pastelBlue);
      monthToday.setBackground(TaskCommander.pastelBlue);
      tabbedPane = new JTabbedPane();
      monthView = new JPanel();
      weekView = new JPanel();
      dayView = new JPanel();
      tabbedPane.add("Month", monthView);
      tabbedPane.add("Week", weekView);
      tabbedPane.add("Day", dayView);
      daysOfMonth = new Day[6][8]; //Create extra row and column for index by 1 purposes
      daysOfWeek = new Day[8]; //Create extra column for index by 1 purposes
      //Integer dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
      //Integer dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
      //Integer weekOfMonth = cal.get(Calendar.WEEK_OF_MONTH);
      //Integer dayOfWeekInMonth = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
      //configureMonthView(cal.get(Calendar.DAY_OF_WEEK), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.WEEK_OF_MONTH), cal.get(Calendar.MONTH));
      //configureWeekView(cal.get(Calendar.DAY_OF_WEEK), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH));
      configureMonthView();
      configureWeekView();
      configureDayView();
    }
    private void configureLayouts()
    {
      setLayout(new BorderLayout());
      monthView.setLayout(new GridBagLayout());
      weekView.setLayout(new GridBagLayout());
      dayView.setLayout(new GridBagLayout());
      GridBagConstraints layoutConstraints = new GridBagConstraints();
    }
    private void addComponents()
    {
      add(tabbedPane, BorderLayout.CENTER);
    }
    private void addListeners()
    {
    }
    private void setToday(int dayOfMonth)
    {
      dayToday = new Day(dayOfMonth);
      weekToday = new Day(dayOfMonth);
      monthToday = new Day(dayOfMonth);
      dayToday.setBackground(TaskCommander.pastelBlue);
      weekToday.setBackground(TaskCommander.pastelBlue);
      monthToday.setBackground(TaskCommander.pastelBlue);
    }
    private void configureDayView()
    {
    }
    private void configureWeekView()
    {
      configureWeekView(today.get(Calendar.DAY_OF_WEEK),
                        today.get(Calendar.DAY_OF_MONTH),
                        today.get(Calendar.MONTH));
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
    private void configureMonthView()
    {
      configureMonthView(today.get(Calendar.DAY_OF_WEEK),
                         today.get(Calendar.DAY_OF_MONTH),
                         today.get(Calendar.WEEK_OF_MONTH),
                         today.get(Calendar.MONTH));
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
            String orig = daysOfMonth[i][j].getDayOfMonth().toString();
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
}
