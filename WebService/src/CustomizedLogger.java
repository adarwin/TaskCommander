package com.adarwin.csc435;

interface CustomizedLogger
{
  void log(Exception ex);
  void log(String level, String message);
}
