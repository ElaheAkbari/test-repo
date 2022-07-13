package com.springbootbackend.employee.controller;

import java.util.*;

import com.springbootbackend.employee.exception.ResourceNotFoundException;
import com.springbootbackend.employee.model.Employee;
import com.springbootbackend.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    //Get All Employee
    @GetMapping("/employees")
    public List<Employee> getAllEmployee() {

        return employeeRepository.findAll();
    }

    //Get Employee By ID
    @GetMapping("/employee/{id}")
    public ResponseEntity <Employee> getEmployeeById(@PathVariable Long id) {

        Employee employee = employeeRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Employee not found by id" +id));
        return ResponseEntity.ok(employee);
    }

    //Create Employee
    @PostMapping("/employee")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }


    //Update Employee
    @PutMapping("/employee/{id}")
    public ResponseEntity <Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employeeDetails) {

        Employee employee = employeeRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Employee not found by id" +id));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());

        Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    //Delete Employee
    @DeleteMapping("/employees/{id}")
    public ResponseEntity <String> deleteEmployee(@PathVariable Long id) {
        employeeRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Employee not found by id" +id));

        employeeRepository.deleteById(id);
        String response = "Deleting is done!";

        return ResponseEntity.ok(response);
     }


}
