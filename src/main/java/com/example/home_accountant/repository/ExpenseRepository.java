package com.example.home_accountant.repository;

import com.example.home_accountant.model.Expense;
import com.example.home_accountant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);

    List<Expense> findByDateBetweenAndUser(LocalDate startDate, LocalDate endDate, User user);
}
