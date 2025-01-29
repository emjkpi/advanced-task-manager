package com.example.home_accountant.controller;

import com.example.home_accountant.dto.ExpenseDTO;
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

    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpenseDTO expenseDTO,
                                                    @RequestHeader("Authorization") String token) {
        String email = JwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        User user = expenseService.getUserByEmail(email);

        System.out.println("Token email: " + email);
        System.out.println("User ID from token: " + user.getId());
        System.out.println("User ID from DTO: " + expenseDTO.getUserId());

        if (!user.getId().equals(expenseDTO.getUserId())) {
            throw new RuntimeException("Access denied");
        }

        Expense expense = new Expense();
        expense.setCategory(expenseDTO.getCategory());
        expense.setDescription(expenseDTO.getDescription());
        expense.setAmount(expenseDTO.getAmount());
        expense.setDate(expenseDTO.getDate());
        expense.setNote(expenseDTO.getNote());
        expense.setUser(user);

        Expense createdExpense = expenseService.createExpense(expense);
        return ResponseEntity.ok(convertToDTO(createdExpense));
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getAllExpenses(@RequestHeader("Authorization") String token) {
        String email = JwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        User user = expenseService.getUserByEmail(email);

        List<ExpenseDTO> expenses = expenseService.getExpensesByUser(user)
                .stream()
                .map(this::convertToDTO)
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

        existingExpense.setCategory(expenseDTO.getCategory());
        existingExpense.setDescription(expenseDTO.getDescription());
        existingExpense.setAmount(expenseDTO.getAmount());
        existingExpense.setDate(expenseDTO.getDate());
        existingExpense.setNote(expenseDTO.getNote());

        Expense updatedExpense = expenseService.updateExpense(id, existingExpense);
        return ResponseEntity.ok(convertToDTO(updatedExpense));
    }

    @GetMapping("/report/pdf")
    public ResponseEntity<?> generatePdfReport(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                               @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                               @RequestHeader("Authorization") String token) {
        String email = JwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        User user = expenseService.getUserByEmail(email);

        List<Expense> expenses = expenseService.getExpensesByDateRangeAndUser(startDate, endDate, user);
        double totalAmount = expenses.stream().mapToDouble(Expense::getAmount).sum();

        byte[] pdfReport = pdfService.generateExpenseReport(expenses, user, totalAmount);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=expense_report.pdf")
                .body(pdfReport);
    }

    private ExpenseDTO convertToDTO(Expense expense) {
        ExpenseDTO dto = new ExpenseDTO();
        dto.setId(expense.getId());
        dto.setCategory(expense.getCategory());
        dto.setDescription(expense.getDescription());
        dto.setAmount(expense.getAmount());
        dto.setDate(expense.getDate());
        dto.setNote(expense.getNote());
        dto.setUserId(expense.getUser().getId());
        return dto;
    }
}