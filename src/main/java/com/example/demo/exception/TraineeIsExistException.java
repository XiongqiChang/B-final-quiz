package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TraineeIsExistException extends RuntimeException {
    private Error error;
}
