package com.example.demo.controller;

import com.example.demo.dto.TrainerDto;
import com.example.demo.exception.Error;
import com.example.demo.exception.TraineeIsExistException;
import com.example.demo.service.TrainerService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrainerController.class)
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
class TrainerControllerTest {

    @MockBean
    private TrainerService trainerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<Object> userJson;


    private TrainerDto trainerDto;


    @BeforeEach
    void setUp() {
        trainerDto = TrainerDto.builder().name("xzmnt").build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(trainerService);
    }


    @Test
    void getTrainerList() throws Exception {
        List<TrainerDto> trainerDtos = new ArrayList<>();
        trainerDtos.add(trainerDto);
        when(trainerService.getTrainerList(false)).thenReturn(trainerDtos);
        mockMvc.perform(get("/trainers" ).param("grouped","false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name",is("xzmnt")));
        verify(trainerService).getTrainerList(false);
    }

    @Test
    void addTrainer() throws Exception {
        TrainerDto trainerDto2 = TrainerDto.builder().id(1L).name("xzmnt").build();
        when(trainerService.addTrainer(trainerDto)).thenReturn(trainerDto2);
        MockHttpServletRequestBuilder mockHttpServletRequest = post("/trainers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson.write(trainerDto).getJson());
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(mockHttpServletRequest)
                .andReturn()
                .getResponse();
        assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        verify(trainerService).addTrainer(trainerDto);
    }

    @Test
    void deleteTrainer() throws Exception {

       mockMvc.perform(delete("/trainers/1"))
               .andExpect(status().isNoContent());

    }

    @Test
    void should_throw_exception_when_trainer_is_exist() throws Exception {
        when(trainerService.addTrainer(trainerDto)).thenThrow(new TraineeIsExistException(new Error("用户名称已经存在")));
        MockHttpServletRequestBuilder mockHttpServletRequest = post("/trainers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson.write(trainerDto).getJson());
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(mockHttpServletRequest)
                .andReturn()
                .getResponse();
        assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }




}
