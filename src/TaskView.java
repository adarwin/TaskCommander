/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

public interface TaskView
{
  public void addCourse(Course course);
  public void addTask(Task task);
  public void addSubTask(SubTask subTask);
  public void removeCourse(Course course);
  public void removeTask(Task task);
  public void removeSubTask(SubTask subTask);

  public void updateTaskColors();
  public void updateTaskInfo();
  public void updateCourseInfo();

  public void showTasksFor(Course course);
  public void showSubTasksFor(Task task);

  public void repaint();
  public void revalidate();
}
