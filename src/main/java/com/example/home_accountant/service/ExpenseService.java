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

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public List<Expense> getExpensesByUser(User user) {
        return expenseRepository.findByUser(user);
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
    }

    public Expense updateExpense(Long id, Expense expense) {
        Expense existingExpense = getExpenseById(id);
        existingExpense.setDescription(expense.getDescription());
        existingExpense.setAmount(expense.getAmount());
        existingExpense.setDate(expense.getDate());
        return expenseRepository.save(existingExpense);
    }

    public List<Expense> getExpensesByDateRangeAndUser(LocalDate startDate, LocalDate endDate, User user) {
        return expenseRepository.findByDateBetweenAndUser(startDate, endDate, user);
    }

    public void deleteExpense(Long expenseId) {
        expenseRepository.deleteById(expenseId);
    }
}
