package com.fidarov.tourservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fidarov.tourservice.dto.TourRequest;
import com.fidarov.tourservice.repository.TourRepository;
import org.junit.ClassRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
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
@TestPropertySource("/application.text.properties")
class TourServiceApplicationTests{
    @Value("${name}")
    private static String DataBaseName;
    @Value("${UserName}")
    private static String UserName;
    @Value("${Password}")
    private static String Password;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TourRepository tourRepository;

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:15.2")
            .withDatabaseName(DataBaseName)
            .withUsername(UserName)
            .withPassword(Password);
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                "spring.datasource.password=" + postgreSQLContainer.getPassword()
        ).applyTo(applicationContext.getEnvironment());

    }
}
    @Test
    void shouldCreateTour() throws Exception {
        TourRequest tourRequest = getTourRequest();
        String tourRequestString = objectMapper.writeValueAsString(tourRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/tour")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tourRequestString))
                .andExpect(status().isCreated());
        Assertions.assertEquals(tourRepository.findAll().size(), tourRepository.findAll().size());
    }
    @Test
    void shouldGetListOfTours() throws Exception {
        TourRequest tourRequest = getTourRequest();
        String tourRequestString = objectMapper.writeValueAsString(tourRequest);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tour")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(tourRequestString))
                .andExpect(status().is2xxSuccessful());
        Assertions.assertEquals(tourRepository.findAll().size(),tourRepository.findAll().size());
    }

    private TourRequest getTourRequest() {
        return TourRequest.builder()
                .name("Танцы с бубном")
                .description("Приглашаю провести выходные над танцами с бубном вокруг линукса")
                .price(BigDecimal.valueOf(0))
                .build();
    }

}
