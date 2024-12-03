package com.simple.storage.service.service;

import com.simple.storage.service.entity.Employee;
import com.simple.storage.service.payload.EmployeeDto;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {


    Employee saveOneEmployee(EmployeeDto employeeDto) throws IOException;

    List<Employee> getAllEmployees();
}
