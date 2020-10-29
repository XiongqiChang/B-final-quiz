package com.example.demo.service;

import com.example.demo.dto.TraineeDto;
import com.example.demo.exception.TraineeNotExistException;
import com.example.demo.pojo.Trainee;
import com.example.demo.repository.TraineeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraineeServiceTest {

    private TraineeService traineeService;
    @Mock
    private TraineeRepository traineeRepository;

    private Trainee trainee;

    @BeforeEach
    void setUp(){
        traineeService = new TraineeService(traineeRepository);
        trainee = trainee.builder().grouped(false).id(1L).name("xqc").build();
    }

    @Test
    void getTraineeList() {
        List<Trainee> trainees = new ArrayList<>();
        trainees.add(trainee);
        when(traineeRepository.findAllByGrouped(false)).thenReturn(trainees);

        List<TraineeDto> traineeList = traineeService.getTraineeList(false);
        assertThat(traineeList.size()).isEqualTo(1);
    }

    @Test
    void should_return_exception_when_trainee_is_not_found(){
        when(traineeRepository.findById(1L)).thenReturn(Optional.empty());
        TraineeNotExistException traineeNotExistException = assertThrows(TraineeNotExistException.class, () -> {
             traineeService.deleteTrainee(1L);
        });
        assertThat(traineeNotExistException.getError().getMessage()).isEqualTo("该学员不存在");
    }

}
