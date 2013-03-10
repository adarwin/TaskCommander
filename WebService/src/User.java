package com.adarwin.csc435;

import com.adarwin.logging.Logbook;

public class User
{
  private String firstName;
  private String username;
  private String password;

  public User()
  {
    firstName = "Andrew";
  }
  public User(String username)
  {
    this.username = username;
  }
  public String getFirstName() { return firstName; }
  public String getUsername() { return username; }
  public void setUsername(String username)
  {
    this.username = username;
  }
  public String getPassword() { return password; }
  public void setPassword(String password)
  {
    this.password = password;
  }
}
