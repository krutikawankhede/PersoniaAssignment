package com.personia.employee.controller;

import com.personia.employee.service.EmployeeService;
import com.personia.employee.entity.AuthenticationRequest;
import com.personia.employee.entity.Employee;
import com.personia.employee.entity.EmployeeCredentials;
import com.personia.employee.service.EmployeeCredentialService;
import com.personia.employee.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class EmployeeControllerTest {

    @InjectMocks
    EmployeeController employeeController;

    @Mock
    EmployeeService employeeService;

    @Mock
    Employee employee;

    @Mock
    AuthenticationRequest authenticationRequest;

    @Mock
    EmployeeCredentialService employeeCredentialService;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    UserDetailsService userDetailsService;

    @Mock
    JwtUtil jwtUtil;


    @Test
    void createHirarchy() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        HashMap<String,String> map = new HashMap<>();
        map.put("Nick","Pete");
        ResponseEntity<Object> responseEntity = employeeController.createHirarchy(map);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    void getHirarchy() {
        HashMap map = new HashMap();
        map.put("Nick","Sophia");
        when(employeeService.getAllEmployee()).thenReturn(map);
        ResponseEntity<?> responseEntity = employeeController.getHirarchy();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(map);
    }

    @Test
    void getHirarchyEmpty() {
        ResponseEntity<?> responseEntity = employeeController.getHirarchy();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
        //assertThat(responseEntity.getBody()).isEqualTo(map);
    }

    @Test
    void testGetSupervisorDetailsNotPresent() {
        ResponseEntity<?> responseEntity =employeeController.getSupervisorDetails("Nick");
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    void testGetSupervisorDetails() {
        ArrayList<String> list = new ArrayList<>();
        Employee employee = new Employee();
        employee.setSupervisor("sophie");
        employee.setName("nick");
        list.add(employee.getSupervisor());
        when(employeeService.getEmployeeSupervisor("nick")).thenReturn(list);
        ResponseEntity<?> responseEntity =employeeController.getSupervisorDetails("nick");
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void testAuthorization() throws Exception {
        employeeCredentialService.saveEmployeeCredentials("krutika","krutika");
        authenticationRequest.setUsername("krutika");
        authenticationRequest.setPassword("krutika");
        EmployeeCredentials employeeCredentials = new EmployeeCredentials("krutika","krutika");
        List<EmployeeCredentials> employeeCredentialsList = new ArrayList<>();
        employeeCredentialsList.add(employeeCredentials);
        when(employeeCredentialService.getEmployeeUsername("krutika")).thenReturn(employeeCredentialsList);
        //when(employeeCredentialService.getEmployeeUsername(authenticationRequest.getUsername())).thenReturn()
        when(authenticationRequest.getUsername()).thenReturn("krutika");
        when(userDetailsService.loadUserByUsername(authenticationRequest.getUsername())).thenReturn(new User(employeeCredentialsList.get(0).getUsername(), employeeCredentialsList.get(0).getPassword(), new ArrayList<>()));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        ResponseEntity<?> responseEntity =employeeController.createAuthenticationToken(authenticationRequest);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void testAuthorizationUnAuthorize() throws Exception {

        EmployeeCredentials employeeCredentials = new EmployeeCredentials("krutika","krutika");
        List<EmployeeCredentials> employeeCredentialsList = new ArrayList<>();
        employeeCredentialsList.add(employeeCredentials);
        when(employeeCredentialService.getEmployeeUsername("krutik")).thenReturn(employeeCredentialsList);
        //when(employeeCredentialService.getEmployeeUsername(authenticationRequest.getUsername())).thenReturn()
        when(authenticationRequest.getUsername()).thenReturn("krutik");
        when(userDetailsService.loadUserByUsername(authenticationRequest.getUsername())).thenReturn(new User(employeeCredentialsList.get(0).getUsername(), employeeCredentialsList.get(0).getPassword(), new ArrayList<>()));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        ResponseEntity<?> responseEntity =employeeController.createAuthenticationToken(authenticationRequest);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(401);
    }



}