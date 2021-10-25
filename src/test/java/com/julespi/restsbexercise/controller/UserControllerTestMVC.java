package com.julespi.restsbexercise.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTestMVC {

    @Autowired
    private MockMvc mockMvc;

    /*@Test
    public void shouldReturnInitUser() throws Exception {
        this.mockMvc.perform(get("/api/initdb"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Julian Spinelli")));
    }*/
}
