package com.example.demo.service;

import com.example.demo.dto.GroupDto;
import com.example.demo.dto.TraineeDto;
import com.example.demo.dto.TrainerDto;
import com.example.demo.exception.Error;
import com.example.demo.exception.GroupUnableException;
import com.example.demo.pojo.Group;
import com.example.demo.pojo.Trainee;
import com.example.demo.pojo.Trainer;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.TraineeRepository;
import com.example.demo.repository.TrainerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GroupService {


    private final GroupRepository groupRepository;
    private final TrainerRepository trainerRepository;
    private final TraineeRepository traineeRepository;
    private final int  GROUP_TRAINER = 2;
    public GroupService(GroupRepository groupRepository,
                        TrainerRepository trainerRepository,
                        TraineeRepository traineeRepository) {
        this.groupRepository = groupRepository;
        this.trainerRepository = trainerRepository;
        this.traineeRepository = traineeRepository;
    }


    public List<GroupDto> getGroup() {
      return groupRepository.findAll().stream()
              .map((item)->GroupDto.builder()
                      .id(item.getId())
                      .name(item.getName())
                      .trainees(transferTrainee(item.getId()))
                      .trainers(transferTrainer(item.getId())).build()).collect(Collectors.toList());
    }


    public List<GroupDto> autoGroup() {
        List<Trainer> trainers = trainerRepository.findAll();
        List<Trainee> trainees = traineeRepository.findAll();
        if (trainers.size() < GROUP_TRAINER){
                throw new GroupUnableException(new Error("无法分组"));
        }
        resetGroup();
        Collections.shuffle(trainees);
        Collections.shuffle(trainers);
       return  initGroupDto(trainers,trainees);
    }


    private List<GroupDto> initGroupDto(List<Trainer> trainers,List<Trainee> trainees){
        List<GroupDto> groupDtoList = new ArrayList<>();
        int groupNum = trainers.size() / GROUP_TRAINER;
        for (int i = 0; i < groupNum; i++){
            groupRepository.save(Group.builder().name(i+ 1 + "组").build());
        }
        List<Group> groups = groupRepository.findAll();
        for (int i = 0; i < groupNum; i++){
            groupDtoList.add(GroupDto.builder().name(groups.get(i).getName())
                    .id(groups.get(i).getId()).trainees(new ArrayList<>())
                    .trainers(new ArrayList<>()).build());
            List<TrainerDto> trainersDtos = groupDtoList.get(i).getTrainers();
            for (int j = 0; j < 2; j++){
                Trainer removeTrainer = trainers.remove(0);
                removeTrainer.setGroup(groups.get(i));
                removeTrainer.setGrouped(true);
                trainerRepository.save(removeTrainer);
                trainersDtos.add(TrainerDto.builder().id(removeTrainer.getId()).name(removeTrainer.getName()).build());
            }
        }
        int index = 0;
        for (Trainee trainee : trainees){
            groupDtoList.get(index).getTrainees().add(TraineeDto.builder()
                    .name(trainee.getName()).id(trainee.getId()).build());
            trainee.setGroup(groups.get(index));
            trainee.setGrouped(true);
            traineeRepository.save(trainee);
            index = (index + 1) % groupNum;
        }
        return groupDtoList;
    }

    private void resetGroup(){
        groupRepository.findAll().forEach(
                group -> {
                    trainerRepository.findAllByGroupId(group.getId()).forEach(
                            trainer -> {
                                trainerRepository.updateTrainer(trainer.getGroup().getId());
                            }
                    );
                    traineeRepository.findAllByGroupId(group.getId()).forEach(
                            trainee -> {
                                traineeRepository.updateTrainee(trainee.getGroup().getId());
                            }
                    );
                }
        );

        groupRepository.deleteAll();
    }


    private List<TraineeDto> transferTrainee(Long groupId){
      return traineeRepository.findAllByGroupId(groupId).stream().map(
               item->TraineeDto.builder().id(item.getId()).name(item.getName()).build()
       ).collect(Collectors.toList());
    }

    private List<TrainerDto> transferTrainer(Long groupId){
        return  trainerRepository.findAllByGroupId(groupId).stream().map(
                item->TrainerDto.builder().id(item.getId()).name(item.getName()).build()
        ).collect(Collectors.toList());
    }


}
