package com.adarwin.csc435;

class UserAlreadyExistsException extends Exception
{
  UserAlreadyExistsException(String message)
  {
    super(message);
  }
}
