package org.example.expensescontrol.model;


import lombok.With;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;

@With
public record Expense(
    @MongoId
    String id,
    String category,
    String supplier,
    double amount,
    boolean cashPayment,
    String description,
    LocalDate date
) {
}
