package com.example.demo.repository;

import com.example.demo.pojo.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TraineeRepository  extends JpaRepository<Trainee,Long > {

    List<Trainee> findAllByGrouped(boolean grouped);

   Optional<Trainee> findByName(String name);

    @Override
    Trainee save(Trainee entity);
}
