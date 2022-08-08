package com.personia.employee.controller;

import com.personia.employee.entity.EmployeeCredentials;
import com.personia.employee.service.EmployeeCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManageEmployeeCredentialController {

    @Autowired
    private EmployeeCredentialService credentialService;

    @PostMapping(value = "/addEmployee")
    public ResponseEntity addEmployee(@RequestBody EmployeeCredentials employeeCredentials) {
        return ResponseEntity.ok(credentialService.saveEmployeeCredentials(employeeCredentials.getUsername(), employeeCredentials.getPassword()));
    }
}
