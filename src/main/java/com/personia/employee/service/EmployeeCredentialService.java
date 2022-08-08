package com.personia.employee.service;

import com.personia.employee.entity.EmployeeCredentials;
import com.personia.employee.repository.EmployeeCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EmployeeCredentialService {


    private final EmployeeCredentialsRepository credentialsRepository;

    private EmployeeCredentials credentials;

    @Autowired
    public EmployeeCredentialService(EmployeeCredentialsRepository repository)
    {
        this.credentialsRepository = repository;
    }

    @Transactional
    public String saveEmployeeCredentials(String username, String password) {
        credentials = new EmployeeCredentials();
        credentials.setUsername(username);
        credentials.setPassword(password);
        credentialsRepository.save(credentials);
        return "Added the employee credentials details successfully";
    }

    public List<EmployeeCredentials> getEmployeeUsername(String name) {
        List<EmployeeCredentials> list = credentialsRepository.findByUsername(name);
        return list;
    }
}
