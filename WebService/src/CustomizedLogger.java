package com.adarwin.csc435;

public interface CustomizedLogger
{
  void log(Exception ex);
  void log(String level, String message);
}
