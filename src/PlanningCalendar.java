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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
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
      setBackground(TaskCommander.selectionColor);
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
  final Calendar today;
  Calendar currentCalendar;
  JPanel monthTab, weekTab, dayTab;
  JPanel monthView, weekView, dayView;
  JPanel monthHeader, weekHeader, dayHeader;
  JTabbedPane tabbedPane;
  JLabel monthHeaderLabel;
  JButton prevMonthButton, nextMonthButton;

  private final boolean DEBUG = true;
  private final String CLASS = "PlanningCalendar";

  private void log(String message)
  {
    TaskCommander.log(CLASS, message);
  }

  // Public Methods
    public PlanningCalendar()
    {
      today = Calendar.getInstance();
      currentCalendar = (Calendar)today.clone();
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
          case Calendar.JANUARY: output = "January"; break;
          case Calendar.FEBRUARY: output = "February"; break;
          case Calendar.MARCH: output = "March"; break;
          case Calendar.APRIL: output = "April"; break;
          case Calendar.MAY: output = "May"; break;
          case Calendar.JUNE: output = "June"; break;
          case Calendar.JULY: output = "July"; break;
          case Calendar.AUGUST: output = "August"; break;
          case Calendar.SEPTEMBER: output = "September"; break;
          case Calendar.OCTOBER: output = "October"; break;
          case Calendar.NOVEMBER: output = "November"; break;
          case Calendar.DECEMBER: output = "December"; break;
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
      //today = Calendar.getInstance();
      //setToday(Calendar.getInstance());
      dayToday = new Day(today);
      weekToday = new Day(today);
      monthToday = new Day(today);
      dayToday.setBackground(TaskCommander.selectionColor);
      weekToday.setBackground(TaskCommander.selectionColor);
      monthToday.setBackground(TaskCommander.selectionColor);
      prevMonthButton = new JButton("<");
      prevMonthButton.setToolTipText("Switch to the previous month");
      nextMonthButton = new JButton(">");
      nextMonthButton.setToolTipText("Switch to the next month");
      tabbedPane = new JTabbedPane();
      monthTab = new JPanel();
      monthView = new JPanel();
      monthHeader = new JPanel();
      monthHeaderLabel = new JLabel(month(today.get(Calendar.MONTH)) + " " + today.get(Calendar.YEAR));

      weekTab = new JPanel();
      weekView = new JPanel();
      weekHeader = new JPanel();

      dayTab = new JPanel();
      dayView = new JPanel();
      dayHeader = new JPanel();



      daysOfMonth = new Day[7][8]; //Create extra row and column for index by 1 purposes
      daysOfWeek = new Day[8]; //Create extra column for index by 1 purposes
    }
    private void configureLayouts()
    {
      setLayout(new BorderLayout());
      monthTab.setLayout(new BorderLayout());
      monthView.setLayout(new GridBagLayout());
      monthHeader.setLayout(new FlowLayout());

      weekTab.setLayout(new BorderLayout());
      weekView.setLayout(new GridBagLayout());
      weekHeader.setLayout(new FlowLayout());

      dayTab.setLayout(new BorderLayout());
      dayView.setLayout(new GridBagLayout());
      dayHeader.setLayout(new FlowLayout());

      GridBagConstraints layoutConstraints = new GridBagConstraints();
    }
    private void addComponents()
    {
      monthHeader.add(prevMonthButton);
      monthHeader.add(monthHeaderLabel);
      monthHeader.add(nextMonthButton);
      tabbedPane.add("Month", monthTab);
      tabbedPane.add("Week", weekTab);
      tabbedPane.add("Day", dayTab);

      monthTab.add(monthHeader, BorderLayout.NORTH);
      monthTab.add(monthView, BorderLayout.CENTER);

      weekTab.add(weekHeader, BorderLayout.NORTH);
      weekTab.add(weekView, BorderLayout.CENTER);

      dayTab.add(dayHeader, BorderLayout.NORTH);
      dayTab.add(dayView, BorderLayout.CENTER);

      add(tabbedPane, BorderLayout.CENTER);
    }
    private void addListeners()
    {
      prevMonthButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          moveMonth(-1);
        }
      });
      nextMonthButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          moveMonth(1);
        }
      });
    }
    private String date(Calendar calendar)
    {
      return dayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)) + ", " +
             month(calendar.get(Calendar.MONTH)) + " " +
             calendar.get(Calendar.DAY_OF_MONTH) + ", " +
             calendar.get(Calendar.YEAR);
    }
    private void moveMonth(int numberToMove)
    {
      if (DEBUG) log("Beginning moveMonth(int numberToMove) method");
      if (DEBUG) log("1. Clone today");
      if (DEBUG) log("   Current date: " + date(currentCalendar));
      Calendar temp = (Calendar)currentCalendar.clone();
      if (DEBUG) log("2. Add " + numberToMove + " month(s) to clone");
      temp.add(Calendar.MONTH, numberToMove);
      if (DEBUG) log("3. Configure Month View using clone");
      configureMonthView(temp);
      if (DEBUG) log("4. Change month label");
      if (DEBUG) log("   Started with " + monthHeaderLabel.getText());
      monthHeaderLabel.setText(month(currentCalendar.get(Calendar.MONTH)) + " " + currentCalendar.get(Calendar.YEAR));
      if (DEBUG) log("   Ended with " + monthHeaderLabel.getText());
      monthView.repaint();
    }
    private void setToday(Calendar calendar)
    {
      //today = calendar;
      dayToday = new Day(today);
      weekToday = new Day(today);
      monthToday = new Day(today);
      dayToday.setBackground(TaskCommander.selectionColor);
      weekToday.setBackground(TaskCommander.selectionColor);
      monthToday.setBackground(TaskCommander.selectionColor);
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
      configureMonthView((Calendar)today.clone());
    }
    private void configureMonthView(Calendar calendar)
    {
      if (DEBUG) log("Beginning configuration of month view...");
      if (DEBUG) log("   Starting Date = " + date(calendar));
      currentCalendar = (Calendar)calendar.clone();
      monthView.removeAll();
      if (DEBUG) log("1. Remove any existing components");
      int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
      int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
      int totalWeeksInMainMonth;

      if (DEBUG) log("2. Add day titles");
      //Add day titles
        for (int i = 1; i < 8; i++)
        {
          JLabel temp = new JLabel(dayOfWeek(i));
          temp.setHorizontalAlignment(SwingConstants.CENTER);
          if (DEBUG) log("   Add day title: " + temp.getText());
          addToMonthView(0.5, 0, i, 0, 1, temp);
        }

      if (DEBUG) log("3. Add days");
      //Add days
        //int daysInMonth = numberOfDaysInMonth(calendar.get(Calendar.MONTH));
        int daysInMonth = numberOfDaysInMonth(calendar.get(Calendar.MONTH));
        if (DEBUG) log("   Determine maximum number of days in given month (" + month(calendar.get(Calendar.MONTH)) + "): " + daysInMonth);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        if (DEBUG) log("   Set calendar to " + date(calendar));
        for (int i = 1; i <= daysInMonth; i++)
        {
          dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
          weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
          if (DEBUG) log("   Create new day: " + date(calendar));
          Day dayToAdd = new Day((Calendar)calendar.clone());
          daysOfMonth[weekOfMonth][dayOfWeek] = dayToAdd;
          if (DEBUG) log("   Add newly created day to month view");
          addToMonthView(dayOfWeek, weekOfMonth, dayToAdd);
          if (DEBUG) log("   Increment day of month by 1");
          if (i < daysInMonth) calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        totalWeeksInMainMonth = weekOfMonth;

      // Add days from previous month
        if (DEBUG) log("4. Add days from previous month");
        if (DEBUG) log("   Current Date: " + date(calendar));
        calendar.add(Calendar.MONTH, -1);
        if (DEBUG) log("   Move to previous month: " + month(calendar.get(Calendar.MONTH)));
        if (DEBUG) log("      Date changes to: " + date(calendar));
        calendar.set(Calendar.DAY_OF_MONTH, numberOfDaysInMonth(calendar.get(Calendar.MONTH)));
        if (DEBUG) log("   Set to last day of that month: " + numberOfDaysInMonth(calendar.get(Calendar.MONTH)));
        if (DEBUG) log("      Date changes to: " + date(calendar));
        for (int i = calendar.get(Calendar.DAY_OF_WEEK); i >= 1; i--)
        {
          dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
          weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
          if (DEBUG) log("   Create new date: " + date(calendar));
          Day dayToAdd = new Day((Calendar)calendar.clone());
          dayToAdd.setGrayedOut();
          if (DEBUG) log("   Add new day to daysOfMonth[][] at coordinates (" + 1 + ", " + dayOfWeek + ")");
          daysOfMonth[1][dayOfWeek] = dayToAdd;
          if (DEBUG) log("   Add new day to month view");
          addToMonthView(i, 1, dayToAdd);
          if (DEBUG) log("   Roll day of month down 1");
          calendar.roll(Calendar.DAY_OF_MONTH, -1);
        }

      // Add days from next month
        if (DEBUG) log("5. Add days from next month");
        if (DEBUG) log("   Current Date: " + date(calendar));
        if (DEBUG) log("   Today's Date: " + date(today));
        calendar.add(Calendar.MONTH, 2);
        if (DEBUG) log("   Advance 2 months to " + month(calendar.get(Calendar.MONTH)));
        if (DEBUG) log("      Date = " + date(calendar));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        if (DEBUG) log("   Move current date to beginning of month");
        if (DEBUG) log("      Date = " + date(calendar));
        for (int i = calendar.get(Calendar.DAY_OF_WEEK); i <= 7; i++)
        {
          dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
          weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
          if (DEBUG) log("   Create new day: " + date(calendar));
          Day dayToAdd = new Day((Calendar)calendar.clone());
          dayToAdd.setGrayedOut();
          if (DEBUG) log("   Add new day to daysOfMonth[][] at coordinates (" + totalWeeksInMainMonth + ", " + dayOfWeek + ")");
          daysOfMonth[totalWeeksInMainMonth][dayOfWeek] = dayToAdd;
          if (DEBUG) log("   Add new day to month view");
          addToMonthView(i, totalWeeksInMainMonth, dayToAdd);
          if (DEBUG) log("   Roll calendar to next day of the month");
          calendar.roll(Calendar.DAY_OF_MONTH, 1);
        }
    }
}
