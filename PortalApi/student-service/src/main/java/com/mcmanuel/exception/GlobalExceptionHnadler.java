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


    @ExceptionHandler(StudentNotFoundException.class)
    public ProblemDetail studentNotFoundException(StudentNotFoundException ex){
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,ex.getMessage());

        detail.setTitle("Student Not Found");
        detail.setProperty("Timestamp", LocalDateTime.now());

        return detail;
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ProblemDetail departmentNotFoundException(DepartmentNotFoundException ex){
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,ex.getMessage());

        detail.setTitle("Department Not Found");
        detail.setProperty("Timestamp", LocalDateTime.now());

        return detail;
    }

    @ExceptionHandler(InvalidRoutingKeyException.class)
    public ProblemDetail invalidRoutingKeyException(InvalidRoutingKeyException ex){
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,ex.getMessage());

        detail.setTitle("Invalid Routing Key");
        detail.setProperty("Timestamp", LocalDateTime.now());

        return detail;
    }

    @ExceptionHandler(InvalidScoreException.class)
    public ProblemDetail invalidScoreException(InvalidScoreException ex){
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,ex.getMessage());

        detail.setTitle("Invalid Score");
        detail.setProperty("Timestamp", LocalDateTime.now());

        return detail;
    }

    @ExceptionHandler(UnknownCategoryHeaderException.class)
    public ProblemDetail unknownCategoryHeaderException(UnknownCategoryHeaderException ex){
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,ex.getMessage());

        detail.setTitle("Unknown Category Header");
        detail.setProperty("Timestamp", LocalDateTime.now());

        return detail;
    }
}
