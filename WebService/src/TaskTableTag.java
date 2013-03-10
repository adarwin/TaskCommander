package com.adarwin.csc435;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.*;

public class TaskTableTag extends SimpleTagSupport
{
  public void doTag() throws JspException, IOException
  {
    JspWriter jspOut = getJspContext().getOut();
    jspOut.print("testString");
  }
}
