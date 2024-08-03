package org.example.expensescontrol.DB;

import org.example.expensescontrol.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpenseRepo extends MongoRepository<Expense, String> {
}
