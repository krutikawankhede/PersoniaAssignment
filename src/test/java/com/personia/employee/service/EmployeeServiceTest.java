package com.personia.employee.service;

import com.personia.employee.repository.EmployeeRepository;
import com.personia.employee.entity.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private Employee employee;

    @Test
    void createEmployee() {
    }
    Employee emp = new Employee();
    List<Employee>  list= new ArrayList<>();

    @Test
    void saveEmployee() {
        HashMap map = new HashMap();
        map.put("Pete","Nick");
        map.put("Nick","Sophia");
        when(employeeService.saveEmployee(map)).thenReturn("Employees are added successfully");
        assertThat(employeeService.saveEmployee(map)).isEqualTo("Employees are added successfully");
    }

    @Test
    void getAllEmployee() {

        emp.setName("Nick");
        emp.setSupervisor("Sophie");
        list.add(emp);
        emp = new Employee();
        emp.setName("Pete");
        emp.setSupervisor("Nick");
        list.add(emp);
        emp = new Employee();
        emp.setName("Sophie");
        emp.setSupervisor("Jonas");
        list.add(emp);
        emp = new Employee();
        emp.setName("Barbara");
        emp.setSupervisor("Nick");
        list.add(emp);

        when(employeeRepository.findAll()).thenReturn(list);
        HashMap hirarchyMap = new HashMap();
        HashMap map = new HashMap();
        map.put("Pete",new HashMap<>());
        map.put("Barbara",new HashMap<>());
        hirarchyMap.put("Nick",map);
        map = new HashMap();
        map.putAll(hirarchyMap);
        hirarchyMap = new HashMap();
        hirarchyMap.put("Sophie",map);
        map = new HashMap();
        map.putAll(hirarchyMap);
        hirarchyMap = new HashMap();
        hirarchyMap.put("Jonas",map);

        assertThat(employeeService.getAllEmployee()).isEqualTo(hirarchyMap);
    }

    @Test
    void getEmployeeSupervisor() {
        Employee emp = new Employee();
        List<Employee>  list= new ArrayList<>();
        emp.setName("Nick");
        emp.setSupervisor("Sophie");
        list.add(emp);
        emp = new Employee();
        emp.setName("Pete");
        emp.setSupervisor("Nick");
        list.add(emp);
        emp = new Employee();
        emp.setName("Sophie");
        emp.setSupervisor("Jonas");
        list.add(emp);
        emp = new Employee();
        emp.setName("Barbara");
        emp.setSupervisor("Nick");
        list.add(emp);

        List<String> listOfSupervisors = new ArrayList<>();
        listOfSupervisors.add("Nick");
        listOfSupervisors.add("Sophie");
        listOfSupervisors.add("Jonas");
        when(employeeRepository.findAll()).thenReturn(list);
        assertThat(employeeService.getEmployeeSupervisor("Nick")).isEqualTo(listOfSupervisors);
    }
}