package com.example.demo.domain.service;

import com.example.demo.core.configuration.AppConfiguration;
import com.example.demo.core.model.Device;
import com.example.demo.core.model.GPS;
import com.example.demo.core.model.User;
import com.example.demo.core.repository.DeviceRepository;
import com.example.demo.core.repository.GPSRepository;
import com.example.demo.core.repository.UserRepository;
import com.example.demo.core.repository.projection.GPSSummary;
import com.example.demo.domain.dto.GPSDTO;
import com.example.demo.domain.dto.GPSSummaryDTO;
import com.example.demo.domain.dto.MetaDataDTO;
import com.example.demo.domain.dto.Paging;
import com.example.demo.domain.exception.ParseGPSFileException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GPSServiceTest {
    private static final String UPLOAD_DIR = "/tmp/gps";
    private static final String SAMPLE_GPX = "sample.gpx";

    @Mock
    private GPSRepository gpsRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    DeviceRepository deviceRepository;

    @Mock
    AppConfiguration appConfiguration;

    @Mock
    GPS gps;

    private GPSService gpsService;


    @BeforeAll
    public static void tearUp() throws IOException, URISyntaxException {
        Files.createDirectories(Paths.get(UPLOAD_DIR));
        Files.copy(Paths.get(ClassLoader.getSystemResource("sample.gpx").toURI()), Paths.get(UPLOAD_DIR, SAMPLE_GPX), StandardCopyOption.REPLACE_EXISTING);
    }

    @AfterAll
    public static void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(UPLOAD_DIR, SAMPLE_GPX));
        Files.deleteIfExists(Paths.get(UPLOAD_DIR));
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        gpsService = new GPSService(gpsRepository, userRepository, deviceRepository, appConfiguration);
    }

    @Test
    public void givenGPSDTOWithoutUserAndParseFromFileIsTrue_whenCreatedSuccess_thenSetDefaultUserAndReturnNewGPSDTO() throws ParseGPSFileException, IOException, URISyntaxException {
        GPSDTO dto = new GPSDTO();
        dto.setFile(SAMPLE_GPX);
        dto.setMetaData(MetaDataDTO.newBuilder().withAuthor("").build());

        Device device = new Device(1, "Test1", "link");

        when(gps.toDTO()).thenReturn(new GPSDTO());
        when(appConfiguration.getUploadDir()).thenReturn(UPLOAD_DIR);
        when(gpsRepository.save(Mockito.isA(GPS.class))).thenReturn(gps);
        when(deviceRepository.save(Mockito.isA(Device.class))).thenReturn(device);

        User user = new User(1, GPSService.ANONYMOUS_USERNAME);

        when(userRepository.getFirstByName(GPSService.ANONYMOUS_USERNAME)).thenReturn(user);

        gpsService.save(dto, true);

        ArgumentCaptor<GPS> gpsCapture = ArgumentCaptor.forClass(GPS.class);
        verify(gps, times(1)).toDTO();
        verify(deviceRepository, times(1)).save(Mockito.isA(Device.class));
        verify(userRepository, times(1)).getFirstByName(GPSService.ANONYMOUS_USERNAME);
        verify(gpsRepository, times(1)).save(gpsCapture.capture());

        XmlMapper mapper = new XmlMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        GPSDTO expected = mapper.readValue(Paths.get(UPLOAD_DIR, SAMPLE_GPX).toFile(), GPSDTO.class);
        expected.getMetaData().setAuthor(GPSService.ANONYMOUS_USERNAME);
        expected.getMetaData().setDevice(device.toDTO());
        verify(gpsRepository, times(1)).save(expected.toModel());

        GPS savingGPS = gpsCapture.getValue();
        savingGPS.getTracks().forEach(t -> assertNotNull(t.getGps()));
        savingGPS.getWayPoints().forEach(t -> assertNotNull(t.getGps()));
    }

    @Test
    public void givenId_whenCallGPSRepositoryGet_thenReturnGPS() {
        int id = 1;
        GPS gps = new GPS();
        when(gpsRepository.findById(id)).thenReturn(Optional.of(gps));
        GPSDTO actual = gpsService.get(id);
        verify(gpsRepository, times(1)).findById(id);
        assertEquals(actual, gps.toDTO());
    }

    @Test
    public void givenPageAndPageSize_whenCallGPSRepositoryFindAll_thenReturnPageGPS() {
        int page = 0;
        int pageSize = 1;
        long totalRecords = 10;

        GPSSummary gpsSummary = new GPSSummary() {
            @Override
            public int getId() {
                return 1;
            }

            @Override
            public String getName() {
                return "Test";
            }

            @Override
            public String getDescription() {
                return "Description";
            }
        };

        Page<GPSSummary> mockedPage = Mockito.mock(Page.class);
        when(mockedPage.getTotalElements()).thenReturn(totalRecords);
        when(mockedPage.getNumberOfElements()).thenReturn(pageSize);
        when(mockedPage.stream()).thenReturn(Stream.of(gpsSummary));

        when(gpsRepository.findAllByUserNotNull(Mockito.isA(Pageable.class))).thenReturn(mockedPage);
        Paging<GPSSummaryDTO> gpsSummaries = gpsService.findAll(page, pageSize);
        assertEquals(totalRecords, gpsSummaries.getTotalRecords());
        assertEquals(pageSize, gpsSummaries.getReturnedRecords());
        assertEquals(page, gpsSummaries.getCurrentPage());

        GPSSummaryDTO expected = new GPSSummaryDTO(1, "Test", "Description", null);
        assertEquals(expected, gpsSummaries.getData().get(0));
    }
}
