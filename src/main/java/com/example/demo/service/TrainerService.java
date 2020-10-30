package com.example.demo.service;

import com.example.demo.dto.TrainerDto;
import com.example.demo.exception.Error;
import com.example.demo.exception.TraineeIsExistException;
import com.example.demo.exception.TrainerNotExistException;
import com.example.demo.pojo.Trainer;
import com.example.demo.repository.TrainerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrainerService {
    private  final TrainerRepository trainerRepository;

    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public List<TrainerDto> getTrainerList(boolean grouped) {
        return trainerRepository.findAllByGrouped(grouped).stream().map(item->
                TrainerDto.builder().id(item.getId()).name(item.getName()).build()).collect(Collectors.toList());
    }

    public TrainerDto addTrainer(TrainerDto trainerDto) {

        Optional<Trainer> byName = trainerRepository.findByName(trainerDto.getName());
        // TODO GTB-完成度: - 违反需求，应该可以添加名字一样的讲师
        if (byName.isPresent()){
            throw new TraineeIsExistException(new Error("用户名称已经存在"));
        }
        Trainer trainer = Trainer.builder().name(trainerDto.getName()).grouped(false).build();
        Trainer newTrainer = trainerRepository.save(trainer);
        return  TrainerDto.builder().name(newTrainer.getName()).id(newTrainer.getId()).build();

    }

    public void deleteTrainer(Long id) {

        Optional<Trainer> trainerById = trainerRepository.findById(id);
        if (!trainerById.isPresent()){
            throw  new TrainerNotExistException(new Error("讲师不存在"));
        }
        trainerRepository.deleteById(id);

    }
}
