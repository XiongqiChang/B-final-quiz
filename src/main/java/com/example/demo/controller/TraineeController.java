package com.example.demo.controller;

import com.example.demo.dto.TraineeDto;
import com.example.demo.service.TraineeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
