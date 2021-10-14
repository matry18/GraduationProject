package com.graduationproject.bosted;

import com.graduationproject.bosted.entity.Resident;
import com.graduationproject.bosted.kafka.KafkaAPI;
import com.graduationproject.bosted.repository.ResidentRepository;
import com.graduationproject.bosted.service.ResidentService;
import com.graduationproject.bosted.testFixtures.ResidentFixture;
import com.graduationproject.bosted.webservice.ResidentWebService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

;


@RunWith(SpringRunner.class)
@WebMvcTest(ResidentWebService.class)
public class ResidentWebServiceTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ResidentService residentService;
    @MockBean
    private ResidentRepository residentRepository;
    @MockBean
    private KafkaAPI kafkaAPI;

    private Resident resident;

    @BeforeEach
    public void setup(){
        resident = ResidentFixture.createResident();
        when(residentRepository.getById("1234")).thenReturn(resident); //use testFixture her instead to make entity
    }

    @Test
    public void residentWebServiceTest_getPerson_requestMethodIsPost_shouldReturn405MethodNotAllowed() throws Exception {
        mockMvc.perform(post("/bosted/citizen/1234"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void residentWebServiceTest_getPerson_requestMethodIsPost_shouldReturn200OK() throws Exception {
        resident = ResidentFixture.createResident();
        when(residentRepository.getById("1234")).thenReturn(resident);
        mockMvc.perform(get("/bosted/citizen/1234"))
                .andExpect(status().isOk());
    }
}
