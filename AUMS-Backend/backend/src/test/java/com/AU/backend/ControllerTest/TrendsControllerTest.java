package com.AU.backend.ControllerTest;
import com.AU.backend.Controller.TrendsController;
import com.AU.backend.Model.Trends;
import com.AU.backend.Service.TrendsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TrendsController.class)

public class TrendsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TrendsService trendsService;

    @Autowired
    ObjectMapper objectMapper;


    Trends trends1 = new Trends();
    Trends trends2 = new Trends();
    List<Trends> trendsList = new ArrayList<>();

    @BeforeEach
    void init() {
        trends1.setValue(1);
        trends1.setName("java");

        trends2.setName("c++");
        trends2.setValue(2);

        trendsList.add(trends1);
        trendsList.add(trends2);
    }

    @Test
    public void getTrendingSkills() throws Exception {
        lenient().when(trendsService.getTrendingSkills()).thenReturn(trendsList);

        mockMvc.perform(get("/trends/getTrendingSkills"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getCourseRating() throws Exception {
        lenient().when(trendsService.getCourseRating()).thenReturn(trendsList);

        mockMvc.perform(get("/trends/getCourseRating"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
