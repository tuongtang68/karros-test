package com.example.demo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.GreaterThan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class DemoApplicationIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenGPSFile_whenUploadedSuccess_returnNewGPS() throws Exception {
        System.out.println(getClass().getClassLoader().toString());
        MockMultipartFile file = new MockMultipartFile("file", getClass().getClassLoader().getResourceAsStream("./sample.gpx"));
        mockMvc.perform(multipart("/gps").file(file))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id", greaterThan(0)));

    }
}
