package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {

    private Long id;

    private String name;

    private List<TraineeDto> traineeDtoList;

    private List<TrainerDto> trainerDtoList;
}
