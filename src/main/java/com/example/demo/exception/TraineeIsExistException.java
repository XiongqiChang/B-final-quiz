package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TraineeIsExistException extends RuntimeException {
    private Error error;
}
