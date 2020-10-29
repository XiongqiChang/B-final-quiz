package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TraineeNotExistException extends RuntimeException {
    private Error error;
}
