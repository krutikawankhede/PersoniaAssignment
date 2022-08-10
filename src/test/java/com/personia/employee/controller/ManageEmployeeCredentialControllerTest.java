package com.personia.employee.controller;

import com.personia.employee.entity.EmployeeCredentials;
import com.personia.employee.service.EmployeeCredentialService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ManageEmployeeCredentialControllerTest {

    @InjectMocks
    ManageEmployeeCredentialController credentialController;

    @Mock
    EmployeeCredentialService employeeCredentialService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addEmployee() {
        EmployeeCredentials employeeCredentials = new EmployeeCredentials("krutika","krutika");
        ResponseEntity<?> responseEntity =credentialController.addEmployee(employeeCredentials);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void testAddEmployee() throws Exception {

        EmployeeCredentials employee1 = EmployeeCredentials.builder().username("cris").password("cris").build();

        HashMap hashMap = new HashMap();
        hashMap.put("Nick","Sophie");


        MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/addEmployee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee1.toString()))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }
}