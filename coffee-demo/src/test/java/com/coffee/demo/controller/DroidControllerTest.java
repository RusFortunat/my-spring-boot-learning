package com.coffee.demo.controller;

import com.coffee.demo.entity.Droid;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DroidController.class)
public class DroidControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Droid droid;

    @Test
    public void getAndReturnExpectedDroid() throws Exception {
        when(droid.getId()).thenReturn("R2-D2");
        when(droid.getDescription()).thenReturn("My cute little android");

        mockMvc.perform(get("/droid"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("R2-D2"))
            .andExpect(jsonPath("$.description").value("My cute little android"));
    }
}
