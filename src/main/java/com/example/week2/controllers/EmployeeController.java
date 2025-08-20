package com.example.week2.controllers;
import com.example.week2.dto.EmployeeDto;
import com.example.week2.exceptios.ResourceNotFoundException;
import com.example.week2.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeServices;

//    Get All Employees
    @GetMapping(path = "/allEmployee")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        return ResponseEntity.ok(employeeServices.getAllEmployees());
    }

    @GetMapping
    public ResponseEntity<EmployeeDto> getEmployeeById(@RequestParam Long id){
        Optional<EmployeeDto> employee = employeeServices.getEmployeeById(id);
        return employee
                .map(ResponseEntity::ok)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found with id: "+id));
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody @Valid EmployeeDto inputEmployee){
        EmployeeDto savedEmplyee = employeeServices.addEmployee(inputEmployee);
       return new ResponseEntity<>(savedEmplyee, HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody @Valid EmployeeDto updatedEmployee){
        return ResponseEntity.ok(employeeServices.updatedEmployee(updatedEmployee));
    }

    @PatchMapping(path = "/patch/{employeeId}")
    public ResponseEntity<EmployeeDto> updatePartialEmployeeById(@PathVariable Long employeeId, @RequestBody Map<String, Object> update){
        return ResponseEntity.ok(employeeServices.updatePartialEmployeeById(employeeId, update));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long id){
        boolean gotDeleted = employeeServices.deleteEmployeeById(id);
        if(gotDeleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

}
