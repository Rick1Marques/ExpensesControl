package org.example.expensescontrol.service;

import org.example.expensescontrol.DB.ExpenseRepo;
import org.example.expensescontrol.model.Expense;
import org.example.expensescontrol.model.ExpenseDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ExpenseServiceTest {

    private final ExpenseRepo mockExpenseRepo = mock(ExpenseRepo.class);
    private final ExpenseService expenseService = new ExpenseService(mockExpenseRepo);

    @Test
    void addExpense() {

        ExpenseDto expenseDto = new ExpenseDto(
                "food",
                "liferando",
                30.70,
                false,
                "",
                LocalDate.of(2024,5,20)
        );

        Expense expense = new Expense(
                null,
                "food",
                "liferando",
                30.70,
                false,
                "",
                LocalDate.of(2024,5,20)
                );

        when(mockExpenseRepo.save(expense)).thenReturn(expense);

        Expense result = expenseService.addExpense(expenseDto);

        verify(mockExpenseRepo).save(expense);

        assertEquals(expense, result);

    }

    @Test
    void removeExpense() throws Exception {

        String id = "1";

        Expense expense = new Expense(
                "1",
                "food",
                "liferando",
                30.70,
                false,
                "",
                LocalDate.of(2024,5,20)
                );

        when(mockExpenseRepo.findById(id)).thenReturn(Optional.of(expense));
        doNothing().when(mockExpenseRepo).deleteById(id);

        String result = expenseService.removeExpense("1");

        verify(mockExpenseRepo).findById(id);
        verify(mockExpenseRepo).deleteById(id);

        assertEquals(id, result);
    }
}