package com.example.demo.repository;

import com.example.demo.pojo.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer,Long> {

    List<Trainer> findAllByGrouped(boolean grouped);

    Optional<Trainer> findByName(String name);

    @Override
    Trainer  save(Trainer entity);

    List<Trainer> findAllByGroupId(Long id);

    @Query(value = "update trainers as t set  t.grouped = FALSE , t.group_id = NULL WHERE group_id  = ?1",nativeQuery = true)
    @Modifying
    void updateTrainer(Long id);
}
