package com.example.home_accountant.mapper;

import com.example.home_accountant.dto.ExpenseDTO;
import com.example.home_accountant.model.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    @Mapping(source = "user.id", target = "userId")
    ExpenseDTO toDto(Expense expense);

    @Mapping(source = "userId", target = "user.id")
    Expense toEntity(ExpenseDTO expenseDTO);
}
