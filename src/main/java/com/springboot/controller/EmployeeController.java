package com.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.exception.ResourceNotFoundException;
import com.springboot.model.Employee;
import com.springboot.repository.EmployeeRepository;



@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	@PostMapping("/addEmployee")
	public Employee addEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
        @DeleteMapping("/deleteEmployee/{id}")
        public String deleteEmployee(@PathVariable Long id) {
            employeeRepository.deleteById(id);
            return "Employee with ID " + id + " has been deleted.";
        }
        @PutMapping("/update/{id}")
        public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
            try {
                // Check if the employee exists in the database
                if (employeeRepository.existsById(employee.getId())) {
                    // Save the updated employee
                    Employee updatedEmployee = employeeRepository.save(employee);
                    return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
                } else {
                    // If the employee with the given ID is not found
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                // Handle exceptions, log or return an appropriate response
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        //get employee by id rest api
        @GetMapping("/employees/{id}")
        public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));

            return ResponseEntity.ok(employee);
        }

}
