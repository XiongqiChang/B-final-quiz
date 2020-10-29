package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handle(MethodArgumentNotValidException e){
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(message));
    }

    @ExceptionHandler(TraineeIsExistException.class)
    public ResponseEntity<Error> handle(TraineeIsExistException e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getError());
    }

    @ExceptionHandler(TrainerNotExistException.class)
    public ResponseEntity<Error> handle(TrainerNotExistException e){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
    }

    @ExceptionHandler(TraineeNotExistException.class)
    public ResponseEntity<Error> handle(TraineeNotExistException e){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getError());
    }
}
