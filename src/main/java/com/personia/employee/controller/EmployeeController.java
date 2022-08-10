package com.personia.employee.controller;

import com.personia.employee.entity.AuthenticationRequest;
import com.personia.employee.entity.AuthenticationResponse;
import com.personia.employee.exeption.ValidationException;
import com.personia.employee.repository.EmployeeRepository;
import com.personia.employee.service.EmployeeCredentialService;
import com.personia.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/personia-service")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeCredentialService employeeCredentialService;


    @PostMapping(value = "/generateToken")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        return ResponseEntity.ok(new AuthenticationResponse(employeeCredentialService.createAuthenticationToken(authenticationRequest)));

    }


    @PostMapping(path = "/employeeRelationship", produces = MediaType.APPLICATION_JSON_VALUE, consumes = "application/json")
    public ResponseEntity<?> getHirarchy(@RequestBody Map<String, String> employee) throws ValidationException {

        if(!employee.isEmpty()) {
            employeeService.saveEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.getAllEmployee());
        }
        throw new ValidationException("Invalid Json!");
    }

    @GetMapping(path = "/getSupervisorDetails/{name}")
    public ResponseEntity<?> getSupervisorDetails(@PathVariable String name) throws ValidationException {

        if (name.isEmpty() || name.equalsIgnoreCase("")) {
            throw new ValidationException("Employee not present with the given name");
        } else if (employeeRepository.findByName(name).isEmpty()) {
            throw new ValidationException("Employee not present with the given name");

        }
        return ResponseEntity.ok(employeeService.getEmployeeSupervisor(name));


    }


}
