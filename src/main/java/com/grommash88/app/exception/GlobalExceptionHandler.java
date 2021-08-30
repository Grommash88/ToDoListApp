package com.grommash88.app.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler
  public final String handleException(Exception ex, Model model) {
    model.addAttribute("ex", ex.getMessage());
    return "exception";
  }
}