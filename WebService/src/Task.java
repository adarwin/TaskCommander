package com.adarwin.csc435;

import com.adarwin.logging.Logbook;
import java.io.Serializable;

public class Task implements Serializable
{
  private String name;
  private String dueDate;

  public Task()
  {
    name = "";
    dueDate = "";
  }
  public Task(String name)
  {
    this.name = name;
    this.dueDate = "";
  }

  public String getName() { return name; }
  public String getDueDate() { return dueDate; }
  public void setName(String name) { this.name = name; }
  public void setDueDate(String dueDate) { this.dueDate = dueDate; }
}
