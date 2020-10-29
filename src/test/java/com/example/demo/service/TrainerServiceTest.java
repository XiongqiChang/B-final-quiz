package com.example.demo.service;

import com.example.demo.dto.TrainerDto;
import com.example.demo.exception.TrainerNotExistException;
import com.example.demo.pojo.Trainer;
import com.example.demo.repository.TrainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class TrainerServiceTest {

    private TrainerService trainerService;
    @Mock
    private TrainerRepository trainerRepository;

    private Trainer trainer;

    @BeforeEach
    void setUp(){
        trainerService = new TrainerService(trainerRepository);
        trainer = trainer.builder().grouped(false).name("zmt").build();
    }
    @Test
    void addTrainer() {
        TrainerDto trainerDto = TrainerDto.builder().name("zmt").build();
        when(trainerRepository.save(trainer)).thenReturn(trainer);
        TrainerDto trainerDto1 = trainerService.addTrainer(trainerDto);
        assertThat(trainerDto1.getName()).isEqualTo(trainer.getName());
        verify(trainerRepository).save(any());
    }

   @Test
   void should_return_exception_when_trainer_is_not_found(){
        when(trainerRepository.findById(1L)).thenReturn(Optional.empty());
       TrainerNotExistException trainerNotExistException = assertThrows(TrainerNotExistException.class, () -> {
           trainerService.deleteTrainer(1L);
       });
       assertThat(trainerNotExistException.getError().getMessage()).isEqualTo("讲师不存在");
   }
}
