package com.grommash88.app.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler
  public final String handleException(Exception ex, RedirectAttributes redirectAttributes) {

    redirectAttributes.addFlashAttribute("ex", ex.getMessage());
    logger.debug(ex.getMessage());
    return "redirect:/welcome";
  }
}
