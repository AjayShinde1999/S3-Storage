package com.simple.storage.service.controller;

import com.simple.storage.service.entity.Employee;
import com.simple.storage.service.payload.EmployeeDto;
import com.simple.storage.service.service.BucketService;
import com.simple.storage.service.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private BucketService bucketService;

    @PostMapping("/{bucketName}")
    @ResponseStatus(HttpStatus.CREATED)
    public String createBucket(@PathVariable String bucketName) {
        return bucketService.createOneBucket(bucketName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(EmployeeDto employeeDto) throws IOException {
        return employeeService.saveOneEmployee(employeeDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }
}
