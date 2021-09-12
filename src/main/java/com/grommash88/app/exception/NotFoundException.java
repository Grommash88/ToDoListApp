package com.grommash88.app.exception;

public class NotFoundException extends RuntimeException {

  public NotFoundException(String string) {
    super(string);
  }
}