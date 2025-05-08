package com.sisco.escola.api.resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsInAnyOrder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
/*@Sql("classpath:data.sql") // Assuming you have a data.sql file for testing*/
public class EscolaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void procurarTodasEscolaDeveRetornarTodosOsRecursosClassificadoPorNome()
            throws Exception {
        ResultActions resultActions =
                mockMvc.perform(get("/escolas")
                        .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.[0].name").value("Escola 1"));
        resultActions.andExpect(jsonPath("$.name", containsInAnyOrder("Escola 1", "Escola 2", "Escola 3")));
    }

}