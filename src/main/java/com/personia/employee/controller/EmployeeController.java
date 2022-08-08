package com.personia.employee.controller;

import com.personia.employee.entity.AuthenticationRequest;
import com.personia.employee.entity.AuthenticationResponse;
import com.personia.employee.entity.EmployeeCredentials;
import com.personia.employee.repository.EmployeeRepository;
import com.personia.employee.service.EmployeeCredentialService;
import com.personia.employee.service.EmployeeService;
import com.personia.employee.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private EmployeeCredentialService employeeCredentialService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(value = "/personia-service/createHirarchy", consumes = "application/json")
    public ResponseEntity createHirarchy(@RequestBody Map<String, String> employee) {
        return new ResponseEntity(employeeService.saveEmployee(employee),HttpStatus.CREATED);
    }


    @PostMapping(value = "/personia-service/generateToken")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try {
            List<EmployeeCredentials> employeeCredentialsList = employeeCredentialService.getEmployeeUsername(authenticationRequest.getUsername());
            if(!employeeCredentialsList.isEmpty() && authenticationRequest.getUsername().equalsIgnoreCase(employeeCredentialsList.get(0).getUsername())) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
            }else{
                HttpHeaders headers = new HttpHeaders();
                headers.add("Custom-Header","foo");
                return new ResponseEntity<>("User not authorized to perform this request", headers, HttpStatus.UNAUTHORIZED);
            }
        }catch (BadCredentialsException e)
        {
            throw new Exception("Incorrect username or password",e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }

    @GetMapping(path="/personia-service/getHirarchy")
    public ResponseEntity<?> getHirarchy() {
        if(employeeService.getAllEmployee().isEmpty()){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header","personia");
            return new ResponseEntity<>("No Data available for employees in personia", headers, HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    @GetMapping(path="/personia-service/getSupervisorDetails/{name}")
    public ResponseEntity<?> getSupervisorDetails(@PathVariable String name){

        if(name.isEmpty() || name.equalsIgnoreCase("")){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header","personia");
            return new ResponseEntity<>("No hirarchy available for given employee in personia", headers, HttpStatus.NO_CONTENT);
        }else if(employeeService.getEmployeeSupervisor(name).isEmpty()){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header","personia");
            return new ResponseEntity<>("No Data available for employees in personia", headers, HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(employeeService.getEmployeeSupervisor(name));


    }


}
