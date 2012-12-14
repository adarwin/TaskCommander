/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;

public class MacroRecDialog extends JDialog
{
  private JPanel buttonPanel;
  private JScrollPane scrollPane;
  private JList<Command> commandList;
  private DefaultListModel<Command> listModel;
  private JButton saveButton, cancelButton;

  // Public Methods
    public void addCommand(Command command)
    {
      listModel.addElement(command);
    }
    public void removeCommand(Command command)
    {
      listModel.removeElement(command);
    }

    public MacroRecDialog()
    {
      configureComponents();
      configureLayouts();
      addListeners();
      addComponents();
    }

  // Private Methods
    private void configureComponents()
    {
      listModel = new DefaultListModel<Command>();
      commandList = new JList<Command>(listModel);
      scrollPane = new JScrollPane(commandList);
      buttonPanel = new JPanel();
      saveButton = new JButton("Save");
      cancelButton = new JButton("Cancel");
      setTitle("Recording Macro...");
      setSize(new Dimension(300, 400));
      setLocationRelativeTo(null);
    }
    private void configureLayouts()
    {
      setLayout(new BorderLayout());
      //scrollPane.setLayout(new BorderLayout());
    }
    private void addListeners()
    {
      saveButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          dispose();
        }
      });
      cancelButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          dispose();
        }
      });
    }
    private void addComponents()
    {
      //scrollPane.add(commandList);
      buttonPanel.add(cancelButton);
      buttonPanel.add(saveButton);
      add(scrollPane, BorderLayout.CENTER);
      add(buttonPanel, BorderLayout.SOUTH);
    }
}
