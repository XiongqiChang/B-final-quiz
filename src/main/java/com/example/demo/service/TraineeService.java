package com.example.demo.service;

import com.example.demo.dto.TraineeDto;
import com.example.demo.exception.Error;
import com.example.demo.exception.TraineeIsExistException;
import com.example.demo.exception.TraineeNotExistException;
import com.example.demo.pojo.Trainee;
import com.example.demo.repository.TraineeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TraineeService {

    private final  TraineeRepository traineeRepository;

    public TraineeService(TraineeRepository traineeRepository) {
        this.traineeRepository = traineeRepository;
    }

    public List<TraineeDto> getTraineeList(boolean grouped){
      return   traineeRepository.findAllByGrouped(grouped).stream().map(item->
          TraineeDto.builder().id(item.getId()).name(item.getName()).build()).collect(Collectors.toList());
    }


    public TraineeDto addTrainee(TraineeDto traineeDto) {


        Optional<Trainee> byName = traineeRepository.findByName(traineeDto.getName());
        if (byName.isPresent()){
           throw new TraineeIsExistException(new Error("用户名称已经存在"));
       }
        Trainee trainee = Trainee.builder().name(traineeDto.getName()).grouped(false).build();
        Trainee newTrainee = traineeRepository.save(trainee);
        return  TraineeDto.builder().name(newTrainee.getName()).id(newTrainee.getId()).build();

    }

    public void deleteTrainee(Long id) {

        Optional<Trainee> traineeById = traineeRepository.findById(id);
        if (!traineeById.isPresent()){
            throw  new TraineeNotExistException(new Error("该学员不存在"));
        }
        traineeRepository.deleteById(id);
    }
}
