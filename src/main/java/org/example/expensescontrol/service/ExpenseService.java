package org.example.expensescontrol.service;

import lombok.RequiredArgsConstructor;
import org.example.expensescontrol.DB.ExpenseRepo;
import org.example.expensescontrol.exception.ExpenseNotFoundException;
import org.example.expensescontrol.model.Expense;
import org.example.expensescontrol.model.ExpenseDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

private final ExpenseRepo expenseRepo;

public List<Expense> getExpenses(String category,String supplier,boolean cashPayment,String payDate,String sort){
    List<Expense> expenses = expenseRepo.findAll();



    if (!category.isEmpty()) {
        expenses = expenses.stream()
                .filter(expense -> category.equals(expense.category()))
                .collect(Collectors.toList());
    }

    if (!supplier.isEmpty()) {
        expenses = expenses.stream()
                .filter(expense -> supplier.equals(expense.supplier()))
                .collect(Collectors.toList());
    }

    //verify cashPayment!!!!!!!!!!
    expenses = expenses.stream()
            .filter(expense -> expense.cashPayment() == cashPayment)
            .collect(Collectors.toList());

    if (!payDate.isEmpty()) {
        expenses = expenses.stream()
                .filter(expense -> payDate.equals(expense.date().toString()))
                .collect(Collectors.toList());
    }

    expenses = expenses.stream()
            .sorted(Comparator.comparingDouble(Expense::amount))
            .collect(Collectors.toList());


    if(sort.equals("desc")){
        expenses = expenses.reversed();
    }

    return expenses;
}

public Expense addExpense(ExpenseDto expenseDto){

    Expense newExpense = new Expense(null,
            expenseDto.category(),
            expenseDto.supplier(),
            expenseDto.amount(),
            expenseDto.cashPayment(),
            expenseDto.description(),
            expenseDto.date());

    return expenseRepo.save(newExpense);
}

public String removeExpense(String id) throws ExpenseNotFoundException {
    expenseRepo.findById(id).orElseThrow(()-> new ExpenseNotFoundException(id));
    expenseRepo.deleteById(id);
    return id;
}

public Expense updateExpense(Expense expense) throws ExpenseNotFoundException {
    Expense oldExpense = expenseRepo.findById(expense.id()).orElseThrow(()-> new ExpenseNotFoundException(expense.id()));
    Expense updatedExpense = oldExpense
            .withCategory(expense.category())
            .withSupplier(expense.supplier())
            .withAmount(expense.amount())
            .withCashPayment(expense.cashPayment())
            .withDescription(expense.description())
            .withDate(expense.date());

    return expenseRepo.save(updatedExpense);
}

}
