package com.example.demo.controller;

import com.example.demo.dto.TraineeDto;
import com.example.demo.exception.Error;
import com.example.demo.exception.TraineeIsExistException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        traineeDto = traineeDto.builder().name("xqc").build();
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

    @Test
    void should_add_trainee() throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequest = post("/trainees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson.write(traineeDto).getJson());
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(mockHttpServletRequest)
                .andReturn()
                .getResponse();
        assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        verify(traineeService).addTrainee(traineeDto);
    }

    @Test
    void should_throw_exception_when_trainee_is_exist() throws Exception {
        when(traineeService.addTrainee(traineeDto)).thenThrow(new TraineeIsExistException(new Error("用户名称已经存在")));
        MockHttpServletRequestBuilder mockHttpServletRequest = post("/trainees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson.write(traineeDto).getJson());
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(mockHttpServletRequest)
                .andReturn()
                .getResponse();
        assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void should_throw_exception_when_name_is_null() throws Exception{
        TraineeDto build = TraineeDto.builder().build();
        MockHttpServletRequestBuilder mockHttpServletRequest = post("/trainees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson.write(build).getJson());
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(mockHttpServletRequest)
                .andReturn()
                .getResponse();
        assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


}
