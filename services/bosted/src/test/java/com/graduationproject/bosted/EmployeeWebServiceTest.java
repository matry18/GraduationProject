package com.graduationproject.bosted;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.bosted.dto.EmployeeDto;
import com.graduationproject.bosted.entity.Employee;
import com.graduationproject.bosted.kafka.KafkaAPI;
import com.graduationproject.bosted.repository.EmployeeRepository;
import com.graduationproject.bosted.service.EmployeeService;
import com.graduationproject.bosted.testFixtures.EmployeeFixture;
import com.graduationproject.bosted.webservice.EmployeeWebService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeWebService.class)
public class EmployeeWebServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private KafkaAPI kafkaAPI;

    @MockBean
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup(){
        employee = EmployeeFixture.createEmployee();
        when(employeeRepository.getById("1234")).thenReturn(employee);
    }

    @Test
    public void employeeWebServiceTest_getPerson_requestMethodIsPost_shouldReturn405MethodNotAllowed() throws Exception {
        mockMvc.perform(post("/bosted/employees/1234"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void employeeWebServiceTest_getPerson_requestMethodIsGet_shouldReturnOK() throws Exception {
        employee = EmployeeFixture.createEmployee();
        when(employeeRepository.getById("1234")).thenReturn(employee);
        mockMvc.perform(get("/bosted/employees/1234"))
                .andExpect(status().isOk());
    }

    @Test
    public void employeeWebServiceTest_createEmployee_shouldReturnOK() throws Exception {
        employee = EmployeeFixture.createEmployee();
        EmployeeDto employeeDto = new EmployeeDto(employee);
        ObjectMapper objectMapper = new ObjectMapper();
        RequestBuilder request = MockMvcRequestBuilders
                .post("/bosted/employees")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDto))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(employeeDto)));
    }

    @Test
    public void employeeWebserviceTest_deleteEmployee_shouldReturnOK() throws Exception {
        employee = EmployeeFixture.createEmployee();
        when(employeeService.deleteEmployee(any())).thenReturn(employee);
        EmployeeDto employeeDto = new EmployeeDto(employee);
        ObjectMapper objectMapper = new ObjectMapper();
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/bosted/employees/{id}", "1234")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDto))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(employeeDto)));
    }

    @Test
    public void employeeWebService_getAllEmployees_shouldReturnList() throws  Exception {
        employee = EmployeeFixture.createEmployee();
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        Mockito.when(employeeService.getAllEmployees()).thenReturn(employeeList);
        EmployeeDto employeeDto = new EmployeeDto(employee);
        ObjectMapper objectMapper = new ObjectMapper();
        RequestBuilder request = MockMvcRequestBuilders
                .get("/bosted/employees")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDto))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().equals(Arrays.asList(objectMapper.writeValueAsString(employeeDto)));
    }
}

