package com.adarwin.csc435;

class UserAlreadyExistsException extends Exception
{
  private static final long serialVersionUID = 1L;
  UserAlreadyExistsException(String message)
  {
    super(message);
  }
}
