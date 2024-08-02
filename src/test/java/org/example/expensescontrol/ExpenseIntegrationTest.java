package org.example.expensescontrol;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ExpenseIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Test
    @DirtiesContext
    void getExpenses() throws Exception{
        mvc.perform(MockMvcRequestBuilders
                .get("/api/expenses"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    @DirtiesContext
    void addExpense() throws Exception {

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

}
