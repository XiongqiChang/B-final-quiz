package com.example.demo.repository;

import com.example.demo.pojo.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer,Long> {

    List<Trainer> findAllByGrouped(boolean grouped);

    Optional<Trainer> findByName(String name);

    @Override
    Trainer  save(Trainer entity);
}
