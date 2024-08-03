package org.example.expensescontrol.controller;

import lombok.RequiredArgsConstructor;
import org.example.expensescontrol.model.Expense;
import org.example.expensescontrol.model.ExpenseDto;
import org.example.expensescontrol.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public Expense postExpense(@RequestBody ExpenseDto expenseDto){
        return expenseService.addExpense(expenseDto);
    }

    @GetMapping
    public List<Expense> getExpenses(){
        return expenseService.getExpenses();
    }

    @PutMapping
    public Expense putExpense(@RequestBody Expense expense) {
        return expenseService.updateExpense(expense);
    }
}
