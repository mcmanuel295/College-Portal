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


    @ExceptionHandler(LecturerNotFoundException.class)
    public ProblemDetail lecturerNotFoundException(LecturerNotFoundException ex){
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,ex.getMessage());

        detail.setTitle("Lecturer Not Found");
        detail.setProperty("Timestamp", LocalDateTime.now());

        return detail;
    }
}
