package com.example.demo.domain.controller;

import com.example.demo.domain.dto.GPSDTO;
import com.example.demo.domain.dto.Paging;
import com.example.demo.domain.service.GPSService;
import com.example.demo.domain.service.UploadService;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@WebMvcTest(GPSController.class)
public class GPSControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GPSService service;

    @MockBean
    private UploadService uploadService;

    @Test
    public void givenGPSFile_shouldCallGPSServiceSave_thenReturnNewGPS() throws Exception {
        String uploadFilePath = "/test.gpx";
        MockMultipartFile file = new MockMultipartFile("file", getClass().getClassLoader().getResourceAsStream("sample.gpx"));
        try (MockedConstruction<GPSDTO> mocked = Mockito.mockConstruction(GPSDTO.class)) {
            when(uploadService.uploadFile(Mockito.any(MultipartFile.class))).thenReturn(uploadFilePath);
            when(service.save(Mockito.any(GPSDTO.class), Mockito.eq(true))).thenReturn(Mockito.any(GPSDTO.class));

            mockMvc.perform(multipart("/gps").file(file))
                   .andExpect(status().isOk());


            verify(uploadService, times(1)).uploadFile(file);
            verify(service, times(1)).save(mocked.constructed().get(0), true);
            verify(mocked.constructed().get(0), times(1)).setFile(uploadFilePath);
            assertEquals(1, mocked.constructed().size());
        }
    }

    @Test
    public void givenGPSId_shouldCallGPSService_thenReturnGPS() throws Exception {
        int gpsId = 1;
        when(service.get(1)).thenReturn(new GPSDTO());
        mockMvc.perform(get("/gps/" + gpsId)).andExpect(status().isOk());

        verify(service, times(1)).get(gpsId);
    }

    @Test
    public void givenPageSizeAndPageIndex_shouldCallGPSService_thenReturnListGPSummary() throws Exception {
        int page = 0;
        int pageSize = 10;
        when(service.findAll(page, pageSize)).thenReturn(new Paging<>());

        mockMvc.perform(get(MessageFormat.format("/gps?page={0}&size={1}", page, pageSize))).andExpect(status().isOk());

        verify(service, times(1)).findAll(page, pageSize);
    }
}
