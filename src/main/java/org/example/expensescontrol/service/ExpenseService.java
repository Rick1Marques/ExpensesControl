package org.example.expensescontrol.service;

import lombok.RequiredArgsConstructor;
import org.example.expensescontrol.DB.ExpenseRepo;
import org.example.expensescontrol.model.Expense;
import org.example.expensescontrol.model.ExpenseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

private final ExpenseRepo expenseRepo;

public List<Expense> getExpenses(){
    return expenseRepo.findAll();
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

public String removeExpense(String id) throws Exception {
    expenseRepo.findById(id).orElseThrow(Exception::new);
    expenseRepo.deleteById(id);
    return id;
}

public Expense updateExpense(Expense expense) throws Exception {
    Expense oldExpense = expenseRepo.findById(expense.id()).orElseThrow(Exception::new);
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
