package com.example.home_accountant.mapper;

import com.example.home_accountant.dto.ExpenseDTO;
import com.example.home_accountant.model.Expense;
import com.example.home_accountant.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-30T22:44:07+0100",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class ExpenseMapperImpl implements ExpenseMapper {

    @Override
    public ExpenseDTO toDto(Expense expense) {
        if ( expense == null ) {
            return null;
        }

        ExpenseDTO expenseDTO = new ExpenseDTO();

        expenseDTO.setUserId( expenseUserId( expense ) );
        expenseDTO.setId( expense.getId() );
        expenseDTO.setCategory( expense.getCategory() );
        expenseDTO.setDescription( expense.getDescription() );
        expenseDTO.setAmount( expense.getAmount() );
        expenseDTO.setDate( expense.getDate() );
        expenseDTO.setNote( expense.getNote() );

        return expenseDTO;
    }

    @Override
    public Expense toEntity(ExpenseDTO expenseDTO) {
        if ( expenseDTO == null ) {
            return null;
        }

        Expense expense = new Expense();

        expense.setUser( expenseDTOToUser( expenseDTO ) );
        expense.setId( expenseDTO.getId() );
        expense.setCategory( expenseDTO.getCategory() );
        expense.setDescription( expenseDTO.getDescription() );
        expense.setAmount( expenseDTO.getAmount() );
        expense.setDate( expenseDTO.getDate() );
        expense.setNote( expenseDTO.getNote() );

        return expense;
    }

    private Long expenseUserId(Expense expense) {
        if ( expense == null ) {
            return null;
        }
        User user = expense.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected User expenseDTOToUser(ExpenseDTO expenseDTO) {
        if ( expenseDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( expenseDTO.getUserId() );

        return user;
    }
}
