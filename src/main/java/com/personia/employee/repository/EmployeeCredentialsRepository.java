package com.personia.employee.repository;

import com.personia.employee.entity.EmployeeCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeCredentialsRepository extends JpaRepository<EmployeeCredentials, Integer> {

    public List<EmployeeCredentials> findByUsername(String username);
}
