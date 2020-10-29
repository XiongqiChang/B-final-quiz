package com.example.demo.controller;

import com.example.demo.dto.TrainerDto;
import com.example.demo.service.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TrainerController {
    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping("/trainers")
    public List<TrainerDto> getTrainerList(@RequestParam boolean grouped){
        return trainerService.getTrainerList(grouped);
    }

    @PostMapping("/trainers")
    @ResponseStatus(HttpStatus.CREATED)
    public TrainerDto addTrainer(@RequestBody @Valid TrainerDto trainerDto){

        return trainerService.addTrainer(trainerDto);

    }

    @DeleteMapping("/trainers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrainer(@PathVariable Long id){
        trainerService.deleteTrainer(id);
    }
}
