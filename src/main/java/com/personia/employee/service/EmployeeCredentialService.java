package com.personia.employee.service;

import com.personia.employee.entity.AuthenticationRequest;
import com.personia.employee.entity.EmployeeCredentials;
import com.personia.employee.exeption.ValidationException;
import com.personia.employee.repository.EmployeeCredentialsRepository;
import com.personia.employee.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class EmployeeCredentialService {


    private final EmployeeCredentialsRepository credentialsRepository;

    private EmployeeCredentials credentials;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public EmployeeCredentialService(EmployeeCredentialsRepository repository)
    {
        this.credentialsRepository = repository;
    }

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

    public String createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            List<EmployeeCredentials> employeeCredentialsList = getEmployeeUsername(authenticationRequest.getUsername());
            if (!employeeCredentialsList.isEmpty() && authenticationRequest.getUsername().equalsIgnoreCase(employeeCredentialsList.get(0).getUsername())) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
            } else {
                throw  new ValidationException("Please provide valid credential");
            }
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return jwt;

    }
}
