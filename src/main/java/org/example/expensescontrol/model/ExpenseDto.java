package org.example.expensescontrol.model;

import java.time.LocalDate;

public record ExpenseDto(
        String category,
        String supplier,
        double amount,
        boolean cashPayment,
        String description,
        LocalDate date
) {
}
