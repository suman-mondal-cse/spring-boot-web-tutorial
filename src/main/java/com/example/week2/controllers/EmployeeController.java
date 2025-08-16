package com.example.week2.controllers;
import com.example.week2.dto.EmployeeDto;
import com.example.week2.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeServices;

//    Get All Employees
    @GetMapping(path = "/allEmployee")
    public List<EmployeeDto> getAllEmployees(){
        return employeeServices.getAllEmployees();
    }

    @GetMapping
    public EmployeeDto getEmployeeById(@RequestParam Long id){
        return employeeServices.getEmployeeById(id);
    }

    @PostMapping
    public EmployeeDto addEmployee(@RequestBody EmployeeDto inputEmployee){
        return employeeServices.addEmployee(inputEmployee);
    }

    @PutMapping
    public EmployeeDto updateEmployee(@RequestBody EmployeeDto updatedEmployee){
        return employeeServices.updatedEmployee(updatedEmployee);
    }

    @PatchMapping(path = "/patch/{employeeId}")
    public EmployeeDto updatePartialEmployeeById(@PathVariable Long employeeId, @RequestBody Map<String, Object> update){
        return employeeServices.updatePartialEmployeeById(employeeId, update);
    }

}
