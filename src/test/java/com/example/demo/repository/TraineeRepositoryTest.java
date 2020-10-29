package com.example.demo.repository;

import com.example.demo.pojo.Group;
import com.example.demo.pojo.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TraineeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private  TraineeRepository traineeRepository;

    private Group group;
    @BeforeEach
    void setUp(){

    }


    @Test
    void findAllByGrouped() {
        entityManager.persistAndFlush(Trainee.builder().grouped(false).name("xqc").build());
        entityManager.persistAndFlush(Trainee.builder().grouped(true).name("xqc2").build());
        entityManager.persistAndFlush(Trainee.builder().grouped(false).name("xqc3").build());

        List<Trainee> allByGrouped = traineeRepository.findAllByGrouped(false);
        assertThat(allByGrouped.size()).isEqualTo(2);
    }
}
