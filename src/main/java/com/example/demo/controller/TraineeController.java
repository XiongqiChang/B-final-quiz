package com.example.demo.controller;

import com.example.demo.dto.TraineeDto;
import com.example.demo.service.TraineeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TraineeController {

    private  final  TraineeService traineeService;

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @GetMapping("/trainees")
    public List<TraineeDto> getTrainees(@RequestParam boolean grouped ){
        return  traineeService.getTraineeList(grouped);
    }

    @PostMapping("/trainees")
    @ResponseStatus(HttpStatus.CREATED)
    public TraineeDto addTrainee(@RequestBody @Valid TraineeDto traineeDto){

        return traineeService.addTrainee(traineeDto);

    }
}
