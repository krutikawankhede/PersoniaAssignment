package com.personia.employee.controller;

import com.personia.employee.entity.Employee;
import com.personia.employee.service.EmployeeService;
import com.personia.employee.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerTest {


    @MockBean
    EmployeeService employeeService;


    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    public static final String ARTICLES_URL = "/personia-service/employeeRelationship";
    public static final String TEST_RESOURCE_PATH = "classpath:test_resource/employee/";
    @Test
    void testPost() throws Exception {
        Employee employee1 = Employee.builder().name("Nick").supervisor("Sophie").build();
        Employee employee2 = Employee.builder().name("Sophie").supervisor("Jonas").build();
        given(employeeService.saveEmployee(anyMap())).willReturn(List.of(employee1, employee2));

        HashMap hashMap = new HashMap();
        hashMap.put("Nick","Sophie");
        MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders.post(ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("test_employee_success.json"))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(403, status);

    }


}