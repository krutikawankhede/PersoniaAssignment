package com.personia.employee.service;

import com.personia.employee.entity.Employee;
import com.personia.employee.entity.EmployeeCredentials;
import com.personia.employee.repository.EmployeeCredentialsRepository;
import com.personia.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {


    private EmployeeRepository employeeRepository;

    private EmployeeCredentials credentials;

    @Autowired
    public EmployeeService(EmployeeRepository repository)
    {
        this.employeeRepository = repository;
    }

    private Employee employeeEntity;

    @Transactional
    public String saveEmployee(Map<String, String> employee) {

        for (Map.Entry<String, String> emp : employee.entrySet()) {
            employeeEntity = new Employee();
            employeeEntity.setName(emp.getKey());
            employeeEntity.setSupervisor(emp.getValue());
            employeeRepository.save(employeeEntity);

        }
        return "Employees are added successfully";
    }

    public HashMap getAllEmployee() {

        String root = null;
        String depth = null;
        List<Employee> list = employeeRepository.findAll();  //getting all the employee as 2 column name and supervisor
        if (!list.isEmpty()) {
            HashMap<String, HashMap<String, String>> childMap = new HashMap<>();
            HashMap<String, String> supervisors = new HashMap<>();
            for (Employee emp : list) {
                if (childMap.containsKey(emp.getSupervisor())) {
                    HashMap mapEmp = childMap.get(emp.getSupervisor());
                    mapEmp.put(emp.getName(), new HashMap<>());
                } else {
                    root = emp.getSupervisor();
                    supervisors.put(emp.getName(), emp.getSupervisor());
                    HashMap mapEmp = new HashMap();
                    mapEmp.put(emp.getName(), new HashMap<>());
                    childMap.put(emp.getSupervisor(), mapEmp);
                }
            }
            //finding depth and root first to start with
            for (Map.Entry<String, String> key : supervisors.entrySet()) {
                if (!childMap.containsKey(key.getKey())) {
                    depth = key.getKey();
                }
                if (!supervisors.containsKey(key.getValue())) {
                    root = key.getValue();
                }
            }

            HashMap map = new HashMap();
            map.put(supervisors.get(depth), childMap.get(supervisors.get(depth)));
            List<String> visited = new ArrayList<>();
            visited.add(root);
            while (!visited.contains(supervisors.get(depth))) {
                visited.add(supervisors.get(depth));
                HashMap inMap = new HashMap();
                inMap.putAll(map);
                map = new HashMap();
                depth = supervisors.get(depth);
                map.put(supervisors.get(depth), inMap);
            }
            return map;
        }
        return new HashMap();
    }

    public ArrayList<String> getEmployeeSupervisor(String name) {

        List<Employee> list = employeeRepository.findAll();
        if(!list.isEmpty()) {
            HashMap<String, String> supervisors = new HashMap<>();
            for (Employee emp : list) {
                supervisors.put(emp.getName(), emp.getSupervisor());

            }
            ArrayList<String> supervisorList = new ArrayList<>();
            while (name != null) {
                supervisorList.add(name);
                name = supervisors.get(name);
            }
            return supervisorList;
        }
        return new ArrayList<>();
    }
}
