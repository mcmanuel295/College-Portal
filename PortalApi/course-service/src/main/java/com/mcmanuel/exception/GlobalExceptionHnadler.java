package com.mcmanuel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHnadler {

//    @ExceptionHandler(StudentNotFoundException.class)
//    public ProblemDetail studentNotFoundException(StudentNotFoundException ex){
//        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,ex.getMessage());
//        detail.setProperty("Timestamp", LocalDateTime.now());
//
//        return detail;
//    }


    @ExceptionHandler(CourseNotFoundException.class)
    public ProblemDetail courseNotFoundException(CourseNotFoundException ex){
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,ex.getMessage());

        detail.setTitle("Course Not Found");
        detail.setProperty("Timestamp", LocalDateTime.now());

        return detail;
    }

    @ExceptionHandler(InvalidScoreException.class)
    public ProblemDetail invalidScore(InvalidScoreException ex){
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE,ex.getMessage());

        detail.setTitle("Invalid grade input");
        detail.setProperty("Timestamp", LocalDateTime.now());

        return detail;
    }
}
