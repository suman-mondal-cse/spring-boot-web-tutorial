package com.example.week2.services;
import com.example.week2.dto.EmployeeDto;
import com.example.week2.entities.EmployeeEntity;
import com.example.week2.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

//get all employees
    public List<EmployeeDto> getAllEmployees() {
       List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
       return employeeEntities
               .stream()
               .map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDto.class))
               .collect(Collectors.toList());
    }

// add a new employee
    public EmployeeDto addEmployee(EmployeeDto inputEmployee) {
        EmployeeEntity employeeEntity = modelMapper.map(inputEmployee, EmployeeEntity.class);
        EmployeeEntity savedEmplyee = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmplyee, EmployeeDto.class);

    }

    public Optional<EmployeeDto> getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDto.class));

    }

    public EmployeeDto updatedEmployee(EmployeeDto updatedEmployee) {
        isEmployeeExistById(updatedEmployee.getId());
        EmployeeEntity employeeEntity = modelMapper.map(updatedEmployee, EmployeeEntity.class);
        employeeRepository.save(employeeEntity);
        return modelMapper.map(employeeEntity, EmployeeDto.class);

    }

// check if the employee is present in the database or not
    private boolean isEmployeeExistById(Long id) {
       return employeeRepository.existsById(id);
    }

//    Update the employee partially
    public EmployeeDto updatePartialEmployeeById(Long employeeId, Map<String, Object> update) {
        boolean employeeExistById = isEmployeeExistById(employeeId);
        if(!employeeExistById) return null;
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        update.forEach((field,value)->{
            Field fieldToBeUpdated = ReflectionUtils.getRequiredField(EmployeeEntity.class, field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated,employeeEntity, value);
        });
        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDto.class);
    }

    public boolean deleteEmployeeById(Long id) {
        if(!isEmployeeExistById(id)) return false;
        employeeRepository.deleteById(id);
        return true;
    }
}
