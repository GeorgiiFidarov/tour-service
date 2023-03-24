package com.fidarov.tourservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fidarov.tourservice.dto.TourRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class TourServiceApplicationTests {

    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:15.2");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
        dynamicPropertyRegistry.add("spring.datasource.url",postgreSQLContainer::getJdbcUrl);
    }

    @Test
    void shouldCreateTour() throws Exception {
        TourRequest tourRequest = getTourRequest();

        String tourRequestString = objectMapper.writeValueAsString(tourRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/tour")
                .contentType(MediaType.APPLICATION_JSON)
                .content(tourRequestString))
                .andExpect(status().isCreated());
    }

    private TourRequest getTourRequest() {

        return TourRequest.builder()
                .name("Тур по северной осетии")
                .description("Приедем в даргас будем жарить шашлык")
                .price(BigDecimal.valueOf(1230))
                .build();
    }

}
