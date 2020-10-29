package com.example.demo.repository;

import com.example.demo.pojo.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TraineeRepository  extends JpaRepository<Trainee,Long > {

    List<Trainee> findAllByGrouped(boolean grouped);

   Optional<Trainee> findByName(String name);

    @Override
    Trainee save(Trainee entity);

    List<Trainee> findAllByGroupId(Long id);

    @Query(value = "update trainees as t set  t.grouped = FALSE , t.group_id = NULL WHERE group_id  = ?1",nativeQuery = true)
    @Modifying
    void updateTrainee(Long id);
}
