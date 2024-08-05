package org.example.expensescontrol.controller;

import lombok.RequiredArgsConstructor;
import org.example.expensescontrol.model.Expense;
import org.example.expensescontrol.model.ExpenseDto;
import org.example.expensescontrol.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public List<Expense> getExpenses(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "supplier", required = false) String supplier,
            @RequestParam(value = "cashPayment", required = false) boolean cashPayment,
            @RequestParam(value = "payDate", required = false) String payDate,
            @RequestParam(value = "sort", defaultValue = "asc") String sort
    ){
        return expenseService.getExpenses(category, supplier, cashPayment, payDate, sort);
    }

    @PutMapping
    public Expense putExpense(@RequestBody Expense expense) {
        return expenseService.updateExpense(expense);
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable String id){
        return expenseService.removeExpense(id);
    }
}
