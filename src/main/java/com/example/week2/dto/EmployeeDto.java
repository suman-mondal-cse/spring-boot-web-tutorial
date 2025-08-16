package com.example.week2.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    public Long id;
    public String name;
    public String email;
    public LocalDate joiningDate;

}
