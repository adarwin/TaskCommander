/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JTabbedPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
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
      addMouseListener(new MouseListener()
      {
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mousePressed(MouseEvent e)
        {
          displayDayOfWeek();
        }
        public void mouseReleased(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}
      });
    }
    public Day()
    {
      setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
      setPreferredSize(new Dimension(50, 50));
      setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
      dateLabel = new JLabel("");
      add(dateLabel);
    }

    private void displayDayOfWeek()
    {
      log("Clicked day: " + calendar.get(Calendar.DAY_OF_WEEK));
    }

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
    public void setGrayedOut()
    {
      dateLabel.setForeground(Color.gray);
      setBorder(new MatteBorder(1, 1, 1, 1, Color.lightGray));
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
      configureMonthView();
      configureWeekView();
      configureDayView();
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
          case Calendar.JANUARY: daysInMonth = 31; break;
          case Calendar.FEBRUARY: daysInMonth = 28; break;
          case Calendar.MARCH: daysInMonth = 31; break;
          case Calendar.APRIL: daysInMonth = 30; break;
          case Calendar.MAY: daysInMonth = 31; break;
          case Calendar.JUNE: daysInMonth = 30; break;
          case Calendar.JULY: daysInMonth = 31; break;
          case Calendar.AUGUST: daysInMonth = 31; break;
          case Calendar.SEPTEMBER: daysInMonth = 30; break;
          case Calendar.OCTOBER: daysInMonth = 31; break;
          case Calendar.NOVEMBER: daysInMonth = 30; break;
          case Calendar.DECEMBER: daysInMonth = 31; break;
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
      private String dayOfMonth(Integer dayOfMonth)
      {
        return dayOfMonth.toString();
      }
      private String weekOfMonth(Integer weekOfMonth)
      {
        return weekOfMonth.toString();
      }
      private String month(Integer month)
      {
        String output;
        switch (month)
        {
          case Calendar.JANUARY: output = "Jan"; break;
          case Calendar.FEBRUARY: output = "Feb"; break;
          case Calendar.MARCH: output = "Mar"; break;
          case Calendar.APRIL: output = "Apr"; break;
          case Calendar.MAY: output = "May"; break;
          case Calendar.JUNE: output = "Jun"; break;
          case Calendar.JULY: output = "Jul"; break;
          case Calendar.AUGUST: output = "Aug"; break;
          case Calendar.SEPTEMBER: output = "Sep"; break;
          case Calendar.OCTOBER: output = "Oct"; break;
          case Calendar.NOVEMBER: output = "Nov"; break;
          case Calendar.DECEMBER: output = "Dec"; break;
          default: output = "INVALID";
        }
        return output;
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
      tabbedPane = new JTabbedPane();
      monthView = new JPanel();
      weekView = new JPanel();
      dayView = new JPanel();
      tabbedPane.add("Month", monthView);
      tabbedPane.add("Week", weekView);
      tabbedPane.add("Day", dayView);
      daysOfMonth = new Day[7][8]; //Create extra row and column for index by 1 purposes
      daysOfWeek = new Day[8]; //Create extra column for index by 1 purposes
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
      dayToday = new Day(today);
      weekToday = new Day(today);
      monthToday = new Day(today);
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
      /*
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
      */
    }
    private void addToMonthView(int x, int y, Day dayToAdd)
    {
      addToMonthView(0.5, 0.5, x, y, 1, dayToAdd);
    }
    private void addToMonthView(double weightX, double weightY, int gridX, int gridY, int gridHeight, Component component)
    {
      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.BOTH;
      c.weightx = weightX;
      c.weighty = weightY;
      c.gridx = gridX;
      c.gridy = gridY;
      c.gridheight = gridHeight;
      monthView.add(component, c);
    }
    private void configureMonthView()
    {
      configureMonthView(today);
    }
    private void configureMonthView(Calendar calendar)
    {
      int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
      int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);

      //Add day titles
        for (int i = 1; i < 8; i++)
        {
          JLabel temp = new JLabel(dayOfWeek(i));
          temp.setHorizontalAlignment(SwingConstants.CENTER);
          addToMonthView(0.5, 0, i, 0, 1, temp);
        }

      int daysInMonth = numberOfDaysInMonth(calendar.get(Calendar.MONTH));
      //Add days
        for (int i = 1; i <= daysInMonth; i++)
        {
          calendar.set(Calendar.DAY_OF_MONTH, i);
          dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
          weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
          Day dayToAdd = new Day((Calendar)calendar.clone());
          daysOfMonth[weekOfMonth][dayOfWeek] = dayToAdd;
          addToMonthView(dayOfWeek, weekOfMonth, dayToAdd);
        }

      // Add days from previous month
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        for (int i = calendar.get(Calendar.DAY_OF_WEEK); i >= 1; i--)
        {
          dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
          weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
          Day dayToAdd = new Day((Calendar)calendar.clone());
          dayToAdd.setGrayedOut();
          daysOfMonth[1][dayOfWeek] = dayToAdd;
          addToMonthView(i, 1, dayToAdd);
          calendar.roll(Calendar.DAY_OF_MONTH, -1);
        }

      // Add days from next month
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, numberOfDaysInMonth(calendar.get(Calendar.MONTH)));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        for (int i = calendar.get(Calendar.DAY_OF_WEEK); i <= 7; i++)
        {
          dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
          weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
          Day dayToAdd = new Day((Calendar)calendar.clone());
          dayToAdd.setGrayedOut();
          daysOfMonth[6][dayOfWeek] = dayToAdd;
          addToMonthView(i, 6, dayToAdd);
          calendar.roll(Calendar.DAY_OF_MONTH, 1);
        }
    }
}
