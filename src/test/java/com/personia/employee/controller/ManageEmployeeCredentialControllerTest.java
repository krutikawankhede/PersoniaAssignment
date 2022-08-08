package com.personia.employee.controller;

import com.personia.employee.entity.EmployeeCredentials;
import com.personia.employee.service.EmployeeCredentialService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ManageEmployeeCredentialControllerTest {

    @InjectMocks
    ManageEmployeeCredentialController credentialController;

    @Mock
    EmployeeCredentialService employeeCredentialService;

    @Test
    void addEmployee() {
        EmployeeCredentials employeeCredentials = new EmployeeCredentials("krutika","krutika");
        ResponseEntity<?> responseEntity =credentialController.addEmployee(employeeCredentials);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

}