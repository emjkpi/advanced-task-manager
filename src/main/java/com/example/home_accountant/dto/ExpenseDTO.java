package com.example.home_accountant.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ExpenseDTO {
    private Long id;
    private String category;
    private String description;
    private double amount;
    private LocalDate date;
    private String note;
    private Long userId;
}
