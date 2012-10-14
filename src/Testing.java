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

    /*
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        runGUITest();
      }
    });
    */
  }


  public void runTests()
  {
    runTest(allCommandsShouldBeAddedToAStack());
    /*
    runTest(allUndonCommandsShouldBeAddedToASeparateStack());
    runTest(undoFollowedByRedoShouldResultInOriginalState());
    runTest(allRunAndRedoneCommandsShouldBeAddedToStack());
    */
  }

  public void runTest(TestResult result)
  {
    String passFail = result.passed ? "passed" : "failed";
    System.out.println(result.testName + passFail + ". " + result.info);
  }


  public TestResult allCommandsShouldBeAddedToAStack()
  {
    String testName = "allCommandsShouldBeAddedToAStack";
    //Generate list of commands
    TaskCommander.addCommand(new TaskAddition(null, new TaskWidget()));
    TaskCommander.addCommand(new TaskAddition(null, new TaskWidget()));

    //Check to make sure the results match the expected
    Stack<Command> expected = new Stack<Command>();
    Stack<Command> actual = TaskCommander.getRunCommands();

    Assertion assertion = assertAreEqual(expected, actual);
    TestResult temp = new TestResult(testName, assertion);
    return temp;
  }

  public TestResult allUndonCommandsShouldBeAddedToASeparateStack()
  {
    String testName = "allUndonCommandsShouldBeAddedToASeparateStack";
    Stack<Command> expected = null;
    Stack<Command> actual = null;
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

  public TestResult allRunAndRedoneCommandsShouldBeAddedToStack()
  {
    String testName = "allRunAndRedoneCommandsShouldBeAddedToStack";
    Stack<Command> expected = null;
    Stack<Command> actual = null;
    Assertion assertion = assertAreEqual(expected, actual);
    return new TestResult(testName, assertion);
  }




  private Assertion assertAreEqual(Stack<Command> expected, Stack<Command> actual)
  {
    boolean passed = false;
    String info = "Assertion failed for unknown reasons";
    if (expected.size() != actual.size())
    {
      passed = false;
      info = "The actual stack is not the same size as the expected stack";
    }
    else
    {
      while (!expected.isEmpty())
      {
        Command expectedValue = expected.pop();
        Command actualValue = actual.pop();
        if (!expectedValue.equals(actualValue))
        {
          passed = false;
          info = "Not all commands in the expected and actual stacks are identical";
          break;
        }
      }
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
    TaskEntryPanel taskEntryPanel = new TaskEntryPanel();
    //taskEntryPanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
    taskEntryPanel.addTaskWidget();
    taskEntryPanel.addTaskWidgetDivider("SomeTitle");
    taskEntryPanel.addTaskWidget();
    taskEntryPanel.addTaskWidget();
    taskEntryPanel.addTaskWidget();
    taskEntryPanel.addTaskWidget();
    taskEntryPanel.addTaskWidget();
    taskEntryPanel.addTaskWidget();
    taskEntryPanel.addTaskWidget();
    taskEntryPanel.addTaskWidget();
    taskEntryPanel.addTaskWidget();
    taskEntryPanel.addTaskWidget();
    taskEntryPanel.addTaskWidget();
    taskEntryPanel.addTaskWidget();
    taskEntryPanel.addTaskWidget();
    taskEntryPanel.addTaskWidget();
    contentPane.add(taskEntryPanel, BorderLayout.CENTER);
  }
}
