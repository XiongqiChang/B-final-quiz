package com.example.demo.service;

import com.example.demo.dto.TraineeDto;
import com.example.demo.repository.TraineeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TraineeService {

    private final  TraineeRepository traineeRepository;

    public TraineeService(TraineeRepository traineeRepository) {
        this.traineeRepository = traineeRepository;
    }

    public List<TraineeDto> getTraineeList(boolean grouped){
      return   traineeRepository.findAllByGrouped(grouped).stream().map(item->
          TraineeDto.builder().id(item.getId()).name(item.getName()).build()).collect(Collectors.toList());
    }



}
