package com.example.home_accountant.controller;

import com.example.home_accountant.model.Expense;
import com.example.home_accountant.model.User;
import com.example.home_accountant.service.ExpenseService;
import com.example.home_accountant.service.PdfService;
import com.example.home_accountant.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private PdfService pdfService;

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        Expense createdExpense = expenseService.createExpense(expense);
        return ResponseEntity.ok(createdExpense);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        Expense updatedExpense = expenseService.updateExpense(id, expense);
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/report/pdf")
    public ResponseEntity<?> generatePdfReport(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("userId") Long userId,
            @RequestHeader("Authorization") String token) {

        String email = JwtUtil.getEmailFromToken(token.replace("Bearer ", ""));
        User user = expenseService.getUserById(userId);

        if (!user.getEmail().equals(email)) {
            throw new RuntimeException("Access denied");
        }

        List<Expense> expenses = expenseService.getExpensesByDateRangeAndUser(startDate, endDate, user);
        double totalAmount = expenses.stream().mapToDouble(Expense::getAmount).sum();

        byte[] pdfReport = pdfService.generateExpenseReport(expenses, user, totalAmount);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=expense_report.pdf")
                .body(pdfReport);
    }
}