package com.example.demo.controller;

import com.example.demo.dto.TraineeDto;
import com.example.demo.service.TraineeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TraineeController.class)
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
class TraineeControllerTest {

    @MockBean
    private TraineeService traineeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<Object> userJson;

    private TraineeDto traineeDto;

    @BeforeEach
    void setUp() {
        traineeDto = traineeDto.builder().id(1L).name("xqc").build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(traineeService);
    }

    @Test
    void should_get_trainee_list_grouped_by_false() throws Exception {
        List<TraineeDto> traineeDtos = new ArrayList<>();
        traineeDtos.add(traineeDto);
        when(traineeService.getTraineeList(false)).thenReturn(traineeDtos);
        mockMvc.perform(get("/trainees" ).param("grouped","false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name",is("xqc")));
        verify(traineeService).getTraineeList(false);
    }


}
