package com.example.crud.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ArtistNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleArtistNotFound(
            ArtistNotFoundException ex,
            HttpServletRequest req
    ) {


        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Resource not found");
        problemDetail.setProperty("path", req.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleServerError(
            Exception ex,
            HttpServletRequest req) {

            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage());
            problemDetail.setTitle("Internal server error");
            problemDetail.setDetail("An unexpected error occurred. Please try again later.");
            problemDetail.setProperty("path",req.getRequestURI());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);

         }



}
