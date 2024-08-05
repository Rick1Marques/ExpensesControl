package org.example.expensescontrol;

import org.example.expensescontrol.DB.ExpenseRepo;
import org.example.expensescontrol.model.Expense;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ExpenseIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ExpenseRepo expenseRepo;

    @Test
    @DirtiesContext
    void getExpenses() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/expenses"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    @DirtiesContext
    void postExpense() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                 "category":"food",
                                 "supplier":"lieferando",
                                 "amount":30.70,
                                 "cashPayment":false,
                                 "description":"",
                                 "date":"2024-05-20"
                                 }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                                 {
                                  "category":"food",
                                  "supplier":"lieferando",
                                  "amount":30.70,
                                  "cashPayment":false,
                                  "description":"",
                                  "date":"2024-05-20"
                                 }

                        """))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @DirtiesContext
    void putExpense() throws Exception {

        Expense expense = new Expense(
                "1",
                "food",
                "liferando",
                30.70,
                false,
                "",
                LocalDate.of(2024,5,20)
        );
        expenseRepo.save(expense);

        mvc.perform(MockMvcRequestBuilders
                        .put("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                                        {
                                                        "id": "1",
                                                        "category":"food",
                                                        "supplier":"china rest",
                                                        "amount":35.70,
                                                        "cashPayment":false,
                                                        "description":"",
                                                        "date":"2024-05-20"
                                                        }
                                """)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                                                {
                                                "id": "1",
                                                "category":"food",
                                                "supplier":"china rest",
                                                "amount":35.70,
                                                "cashPayment":false,
                                                "description":"",
                                                "date":"2024-05-20"
                                                }
                        """));
    }

    @Test
    @DirtiesContext
    void deleteExpense() throws Exception {
        Expense expense = new Expense(
                "1",
                "food",
                "liferando",
                30.70,
                false,
                "",
                LocalDate.of(2024,5,20)
        );
        expenseRepo.save(expense);

        mvc.perform(MockMvcRequestBuilders
                .delete("/api/expenses/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
