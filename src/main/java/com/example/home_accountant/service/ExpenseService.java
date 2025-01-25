package com.example.home_accountant.service;

import com.example.home_accountant.model.Expense;
import com.example.home_accountant.model.User;
import com.example.home_accountant.repository.ExpenseRepository;
import com.example.home_accountant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense updateExpense(Long id, Expense expense) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + id));

        existingExpense.setCategory(expense.getCategory());
        existingExpense.setAmount(expense.getAmount());
        existingExpense.setDate(expense.getDate());
        existingExpense.setNote(expense.getNote());
        return expenseRepository.save(existingExpense);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public List<Expense> getExpensesByCategory(String category) {
        return expenseRepository.findByCategory(category);
    }

    public List<Expense> getExpensesByDate(LocalDate date) {
        return expenseRepository.findByDate(date);
    }

    public List<Expense> getExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByDateBetween(startDate, endDate);
    }

    public Double getTotalAmountByDate(LocalDate date) {
        return expenseRepository.getTotalAmountByDate(date);
    }

    public Double getTotalAmountByMonth(int year, int month) {
        return expenseRepository.getTotalAmountByMonth(year, month);
    }

    public Double getTotalAmountByYear(int year) {
        return expenseRepository.getTotalAmountByYear(year);
    }

    public Double getTotalAmountByDateRange(LocalDate startDate, LocalDate endDate) {
        return expenseRepository.getTotalAmountByDateRange(startDate, endDate);
    }

    public List<Expense> getExpensesByDateRangeAndUser(LocalDate startDate, LocalDate endDate, User user) {
        return expenseRepository.findByDateBetweenAndUser(startDate, endDate, user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
    }
}
