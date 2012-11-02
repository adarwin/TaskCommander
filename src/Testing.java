/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.Stack;

public class Testing
{
  class Assertion
  {
    boolean passed;
    String info;
    Assertion()
    {
      passed = false;
      info = "Failed for some unknown reason";
    }
    Assertion(boolean passed, String info)
    {
      this.passed = passed;
      this.info = info;
    }
  }
  class TestResult extends Assertion
  {
    String testName;
    TestResult(String testName, Assertion assertion)
    {
      this.testName = testName;
      this.passed = assertion.passed;
      this.info = assertion.info;
    }
  }





  public static void main(String[] args)
  {
    //Run Tests
    Testing t = new Testing();
    t.runTests();

    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        runGUITest();
      }
    });
    //System.exit(0);
  }


  public void runTests()
  {
    runTest(allCommandsShouldBeAddedToAStack());
    runTest(allUndonCommandsShouldBeAddedToASeparateStack());
    runTest(allRedoneCommandsShouldBeAddedToStack());
    runTest(undoFollowedByRedoShouldResultInOriginalState());
  }

  public void runTest(TestResult result)
  {
    String passFail = result.passed ? "passed" : "failed";
    System.out.println("\n\r" + result.testName + " ................ " +  passFail + "\n\r    " + result.info + "\n\r");
  }


  public TestResult allCommandsShouldBeAddedToAStack()
  {
    String testName = "allCommandsShouldBeAddedToAStack";
    //Generate list of commands
    Command command1 = new TaskAddition(new Task("TaskName", new Course("CourseName")));
    Command command2 = new TaskEdit(null, null);
    Command command3 = new TaskRemoval(null);
    Command command4 = new TaskAddition(new Task("TaskName", new Course("CourseName")));
    TaskCommander.addCommand(command1);
    TaskCommander.addCommand(command2);
    TaskCommander.addCommand(command3);
    TaskCommander.addCommand(command4);

    //Check to make sure the results match the expected
    Stack<Command> expected = new Stack<Command>();
    Stack<Command> actual = TaskCommander.getRunCommands();

    expected.push(command1);
    expected.push(command2);
    expected.push(command3);
    expected.push(command4);

    Assertion assertion = assertAreEqual(expected, actual);
    TestResult temp = new TestResult(testName, assertion);
    return temp;
  }

  public TestResult allUndonCommandsShouldBeAddedToASeparateStack()
  {
    String testName = "allUndonCommandsShouldBeAddedToASeparateStack";

    Command command1 = new TaskAddition(new Task("TaskName", new Course("CourseName")));
    Command command2 = new TaskEdit(null, null);
    Command command3 = new TaskRemoval(null);
    Command command4 = new TaskAddition(new Task("TaskName", new Course("CourseName")));

    TaskCommander.addCommand(command1);
    TaskCommander.addCommand(command2);
    TaskCommander.addCommand(command3);
    TaskCommander.addCommand(command4);

    TaskCommander.undo();
    TaskCommander.undo();
    TaskCommander.undo();
    TaskCommander.undo();

    Stack<Command> expected = new Stack<Command>();
    Stack<Command> actual = TaskCommander.getUndonCommands();
    
    expected.push(command4);
    expected.push(command3);
    expected.push(command2);
    expected.push(command1);

    Assertion assertion = assertAreEqual(expected, actual);
    return new TestResult(testName, assertion);
  }

  public TestResult undoFollowedByRedoShouldResultInOriginalState()
  {
    String testName = "undoFollowedByRedoShouldResultInOriginalState";
    Stack<Command> expected = null;
    Stack<Command> actual = null;
    Assertion assertion = assertAreEqual(expected, actual);
    return new TestResult(testName, assertion);
  }

  public TestResult allRedoneCommandsShouldBeAddedToStack()
  {
    String testName = "allRedoneCommandsShouldBeAddedToStack";

    Command command1 = new TaskAddition(new Task("TaskName", new Course("CourseName")));
    Command command2 = new TaskEdit(null, null);
    Command command3 = new TaskRemoval(null);
    Command command4 = new TaskAddition(new Task("TaskName", new Course("CourseName")));

    TaskCommander.addCommand(command1);
    TaskCommander.addCommand(command2);
    TaskCommander.addCommand(command3);
    TaskCommander.addCommand(command4);

    TaskCommander.undo();
    TaskCommander.undo();
    TaskCommander.redo();

    Stack<Command> expected = new Stack<Command>();
    Stack<Command> actual = TaskCommander.getRunCommands();

    expected.push(command1);
    expected.push(command2);
    expected.push(command3);

    Assertion assertion = assertAreEqual(expected, actual);
    return new TestResult(testName, assertion);
  }




  private Assertion assertAreEqual(Stack<Command> expected, Stack<Command> actual)
  {
    boolean passed = false;
    String info = "Assertion failed for unknown reasons";
    if (expected != null && actual != null)
    {
      if (expected.size() != actual.size())
      {
        passed = false;
        info = "The actual stack is not the same size as the expected stack";
      }
      else
      {
        boolean allEqual = true;
        while (!expected.isEmpty())
        {
          Command expectedValue = expected.pop();
          Command actualValue = actual.pop();
          if (!expectedValue.equals(actualValue))
          {
            allEqual = false;
            passed = false;
            info = "Not all commands in the expected and actual stacks are identical";
            break;
          }
        }
        if (allEqual)
        {
          passed = true;
          info = "The actual and expected stacks match";
        }
      }
    }
    else
    {
      info = "At least one of the input stacks was null";
    }
    return new Assertion(passed, info);
  }







  private static void runGUITest()
  {
    JFrame frame = new JFrame("Testing Window");
    frame.setPreferredSize(new Dimension(800, 600));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    addComponentsTo(frame.getContentPane());
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  private static void addComponentsTo(Container contentPane)
  {
    PlanningCalendar calendar = new PlanningCalendar();
    contentPane.add(calendar, BorderLayout.CENTER);
    /*
    TaskEntryPanel taskEntryPanel = new TaskEntryPanel();
    //taskEntryPanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
    //taskEntryPanel.addTaskWidget();
    //TaskCommander.addTaskWidget();
    taskEntryPanel.addTaskWidgetDivider("SomeTitle");
    contentPane.add(taskEntryPanel, BorderLayout.CENTER);
    */
  }
}
