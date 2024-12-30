package com.itm.ithive.controller;

import com.itm.ithive.exceptions.SomethingWrong;
import com.itm.ithive.exceptions.UserAlreadyExisting;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExisting.class)
    public String handleUserAlreadyExisting(Exception ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", " ");
        return "redirect:/register?error";
    }

    @ExceptionHandler(SomethingWrong.class)
    public ResponseEntity<String> handleSomethingWrongException(SomethingWrong exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        // make an .html that corresponds IT_Hive
    }
}
