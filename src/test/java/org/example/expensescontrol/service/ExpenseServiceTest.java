package org.example.expensescontrol.service;

import org.example.expensescontrol.DB.ExpenseRepo;
import org.example.expensescontrol.model.Expense;
import org.example.expensescontrol.model.ExpenseDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ExpenseServiceTest {

    private final ExpenseRepo mockExpenseRepo = mock(ExpenseRepo.class);
    private final ExpenseService expenseService = new ExpenseService(mockExpenseRepo);


    @Test
    void getExpenses() {

        Expense expense1 = new Expense(
                "1",
                "food",
                "liferando",
                30.70,
                false,
                "",
                LocalDate.of(2024,5,20)
        );

        Expense expense2 = new Expense(
                "2",
                "food",
                "liferando",
                50.90,
                false,
                "",
                LocalDate.of(2024,5,25)
        );

        Expense expense3 = new Expense(
                "3",
                "entertainment",
                "cinemax",
                20.30,
                false,
                "cinema",
                LocalDate.of(2024,7,15)
        );

        List<Expense> expenseList = List.of(expense1, expense2, expense3);

        when(mockExpenseRepo.findAll()).thenReturn(expenseList);

        List<Expense> expenseListFilter = List.of(expense1, expense2);


        List<Expense> result = expenseService.getExpenses("food", "", false, "", "");

        assertEquals(expenseListFilter, result);

    }


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


        Expense expense = new Expense(
                "1",
                "food",
                "liferando",
                30.70,
                false,
                "",
                LocalDate.of(2024,5,20)
                );

        when(mockExpenseRepo.findById(expense.id())).thenReturn(Optional.of(expense));
        doNothing().when(mockExpenseRepo).deleteById(expense.id());

        String result = expenseService.removeExpense("1");

        verify(mockExpenseRepo).findById(expense.id());
        verify(mockExpenseRepo).deleteById(expense.id());

        assertEquals(expense.id(), result);
    }

    @Test
    void updateExpense() throws Exception {

        Expense expense = new Expense(
                "1",
                "food",
                "liferando",
                30.70,
                false,
                "",
                LocalDate.of(2024,5,20)
        );

        when(mockExpenseRepo.findById(expense.id())).thenReturn(Optional.of(expense));
        when(mockExpenseRepo.save(expense)).thenReturn(expense);

        Expense result = expenseService.updateExpense(expense);

        verify(mockExpenseRepo).findById(expense.id());
        verify(mockExpenseRepo).save(expense);

        assertEquals(expense, result);
    }
}