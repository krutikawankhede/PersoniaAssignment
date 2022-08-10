package com.personia.employee.service;

import com.personia.employee.entity.EmployeeCredentials;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PersoniaUserDetailsServiceTest {

    @InjectMocks
    PersoniaUserDetailsService personiaUserDetailsService;

    @Mock
    UserDetailsService userDetailsService;
    @Mock
    private EmployeeCredentialService employeeCredentialService;
    @Test
    void loadUserByUsername() {
        EmployeeCredentials employeeCredentials = new EmployeeCredentials("krutika","password");
        List<EmployeeCredentials> employeeCredentialsList = new ArrayList<>();
        employeeCredentialsList.add(employeeCredentials);
        when(employeeCredentialService.getEmployeeUsername("krutika")).thenReturn(employeeCredentialsList);

    }
}