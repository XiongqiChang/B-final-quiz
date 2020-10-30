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
// TODO GTB-知识点: + Service上使用了事务注解，good
@Transactional
public class TraineeService {

    private final  TraineeRepository traineeRepository;

    public TraineeService(TraineeRepository traineeRepository) {
        this.traineeRepository = traineeRepository;
    }

    public List<TraineeDto> getTraineeList(boolean grouped){
        // TODO GTB-工程实践: - 建议把类型转换的代码抽成方法，提高可读性
        // TODO GTB-工程实践: - Stream每个方法建议新起一行，提高可读性
      return   traineeRepository.findAllByGrouped(grouped).stream().map(item->
          TraineeDto.builder().id(item.getId()).name(item.getName()).build()).collect(Collectors.toList());
    }


    public TraineeDto addTrainee(TraineeDto traineeDto) {


        Optional<Trainee> byName = traineeRepository.findByName(traineeDto.getName());
        // TODO GTB-完成度: - 违反需求，应该可以添加名字一样的学员
        if (byName.isPresent()){
           throw new TraineeIsExistException(new Error("用户名称已经存在"));
       }
        Trainee trainee = Trainee.builder().name(traineeDto.getName()).grouped(false).build();
        Trainee newTrainee = traineeRepository.save(trainee);
        return  TraineeDto.builder().name(newTrainee.getName()).id(newTrainee.getId()).build();

    }

    public void deleteTrainee(Long id) {

        Optional<Trainee> traineeById = traineeRepository.findById(id);
        // TODO GTB-知识点: - 可以使用orElseThrow方法
        if (!traineeById.isPresent()){
            throw  new TraineeNotExistException(new Error("该学员不存在"));
        }
        traineeRepository.deleteById(id);
    }
}
