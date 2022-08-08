package com.personia.employee.service;

import com.personia.employee.entity.EmployeeCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersoniaUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeCredentialService employeeCredentialService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<EmployeeCredentials> employeeCredentialsList = employeeCredentialService.getEmployeeUsername(username);
        if (!employeeCredentialsList.isEmpty()) {
            return new User(employeeCredentialsList.get(0).getUsername(), employeeCredentialsList.get(0).getPassword(), new ArrayList<>());
        } else {
            return null;
        }
    }
}
