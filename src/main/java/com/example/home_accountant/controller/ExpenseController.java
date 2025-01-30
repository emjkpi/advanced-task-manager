package com.example.home_accountant.controller;

import com.example.home_accountant.dto.ExpenseDTO;
import com.example.home_accountant.mapper.ExpenseMapper;
import com.example.home_accountant.model.Expense;
import com.example.home_accountant.model.User;
import com.example.home_accountant.service.ExpenseService;
import com.example.home_accountant.service.PdfService;
import com.example.home_accountant.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private ExpenseMapper expenseMapper;

    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpenseDTO expenseDTO,
                                                    @RequestHeader("Authorization") String token) {
        String email = JwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        User user = expenseService.getUserByEmail(email);

        if (!user.getId().equals(expenseDTO.getUserId())) {
            throw new RuntimeException("Access denied");
        }

        Expense expense = expenseMapper.toEntity(expenseDTO);
        expense.setUser(user);

        Expense createdExpense = expenseService.createExpense(expense);
        return ResponseEntity.ok(expenseMapper.toDto(createdExpense));
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getAllExpenses(@RequestHeader("Authorization") String token) {
        String email = JwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        User user = expenseService.getUserByEmail(email);

        List<ExpenseDTO> expenses = expenseService.getExpensesByUser(user)
                .stream()
                .map(expenseMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(expenses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDTO> updateExpense(@PathVariable Long id, @RequestBody ExpenseDTO expenseDTO,
                                                    @RequestHeader("Authorization") String token) {
        String email = JwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        User user = expenseService.getUserByEmail(email);

        Expense existingExpense = expenseService.getExpenseById(id);
        if (!existingExpense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        Expense updatedExpense = expenseMapper.toEntity(expenseDTO);
        updatedExpense.setId(id);
        updatedExpense.setUser(user);

        Expense savedExpense = expenseService.updateExpense(id, updatedExpense);
        return ResponseEntity.ok(expenseMapper.toDto(savedExpense));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id,
                                                @RequestHeader("Authorization") String token) {
        String email = JwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        User user = expenseService.getUserByEmail(email);

        Expense expense = expenseService.getExpenseById(id);
        if (!expense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        expenseService.deleteExpense(id);
        return ResponseEntity.ok("Expense deleted successfully");
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestHeader("Authorization") String token) {
        String email = JwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        User user = expenseService.getUserByEmail(email);

        List<ExpenseDTO> expenses = expenseService.getExpensesByDateRangeAndUser(startDate, endDate, user)
                .stream()
                .map(expenseMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(expenses);
    }
}