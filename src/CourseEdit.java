/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.util.ArrayList;

public class CourseEdit implements Command
{
  private ArrayList<TaskView> taskViewsToUpdate;
  private Course previousState, currentState, newState;
  private boolean DEBUG = true;
  private String CLASS = "CourseEdit";

  private void log(String message)
  {
    TaskCommander.log(CLASS, message);
  }

  public CourseEdit(Course previous, Course edited)
  {
    taskViewsToUpdate = TaskCommander.getRegisteredTaskViews();
    previousState = new Course("");
    previousState.updateFrom(previous);
    currentState = previous;
    newState = edited;
  }

  public void run()
  {
    if (DEBUG) log("Now running CourseEdit command");
    if (taskViewsToUpdate != null)
    {
      currentState.updateFrom(newState);
      for (TaskView taskView : taskViewsToUpdate)
      {
        taskView.updateCourseInfo();
        taskView.updateTaskColors();
      }
    }
  }
  public void undo()
  {
    if (DEBUG) log("Attempting to undo CourseEdit");
    currentState.updateFrom(previousState);
    for (TaskView taskView : taskViewsToUpdate)
    {
      taskView.updateCourseInfo();
      taskView.updateTaskColors();
    }
  }
  public void redo()
  {
    if (DEBUG) log("Attempting to redo this CourseEdit");
    run();
  }
}
