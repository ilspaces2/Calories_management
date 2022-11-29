package ru.javawebinar.topjava.web;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ResourceControllerTest extends AbstractControllerTest {

    @Test
    void getStyle() throws Exception {
        perform(get("/resources"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:resources/css/style.css"))
                .andExpect(redirectedUrl("resources/css/style.css"))
                .andExpect(content().contentType("text/css;charset=UTF-8"));
    }
}
